package com.example.autoservice.controller;

import org.springframework.security.core.GrantedAuthority;

import com.example.autoservice.dto.EmployeeDTO;
import com.example.autoservice.dto.ScheduleDTO;
import com.example.autoservice.mapper.EmployeeMapper;
import com.example.autoservice.mapper.ScheduleMapper;
import com.example.autoservice.model.User;
import com.example.autoservice.service.EmployeeService;
import com.example.autoservice.service.ScheduleService;
import com.example.autoservice.service.UserService;
import com.example.autoservice.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;


@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService empService;
    private final AdminService adminService;
    private final ScheduleService schedService;
    private final UserService     userService;
    private final EmployeeMapper  empMapper;
    private final ScheduleMapper  schedMapper;

    /* ---------- публичный список сотрудников ---------- */
    @GetMapping("/employees")
    public String listEmployees(Model model) {

        List<EmployeeDTO> employees = empService.getAll()
                .stream()
                .map(empMapper::toDto)
                .toList();

        model.addAttribute("employees", employees);
        return "employees";
    }

    /* ---------- личный кабинет ---------- */

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetails ud, Model model) {
        if (ud == null) {
            return "error/401";
        }

        // 1) Подгружаем пользователя
        User user   = userService.findByEmail(ud.getUsername());
        Long userId = user.getId();

        if (adminService.isAdmin(userId)) {
            return "redirect:/admin";
        }

        model.addAttribute("isAuthenticated", true);
        model.addAttribute("isAdmin",         false);

        var employee   = empService.getByUserId(userId);
        boolean isEmployee = employee != null;
        model.addAttribute("isEmployee",      isEmployee);
        model.addAttribute("user",            user);
        model.addAttribute("userId",          userId);

        // 5) Если сотрудник — подгружаем данные
        if (isEmployee) {
            // преобразовываем employee в DTO, расписание в DTO и т.д.
            model.addAttribute("employee", empMapper.toDto(employee));
            model.addAttribute("schedules",
                    schedService.getByEmployeeId(userId)
                            .stream()
                            .map(schedMapper::toDto)
                            .toList()
            );
        }

        // 6) И возвращаем клиентский профиль
        return "profile";
    }

    /* ---------- аватар из БД или placeholder ---------- */
    @GetMapping(value = "/avatar/{id}", produces = MediaType.ALL_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> avatar(@PathVariable Long id) throws IOException {

        byte[] photo = userService.findById(id)           // Optional<User>
                .map(User::getPhoto)     // byte[] | null
                .orElse(null);

        if (photo == null || photo.length == 0) {
            // fallback: /static/images/employee-placeholder.jpg
            var placeholder = new ClassPathResource("static/images/employee-placeholder.jpg");
            photo = StreamUtils.copyToByteArray(placeholder.getInputStream());
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(photo);
        }

        /* very small sniff – PNG header 0x89 P N G */
        MediaType type = (photo[0] == (byte) 0x89 && photo[1] == 0x50)
                ? MediaType.IMAGE_PNG
                : MediaType.IMAGE_JPEG;

        return ResponseEntity.ok()
                .contentType(type)
                .body(photo);
    }
}
