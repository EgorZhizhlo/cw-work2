package com.example.autoservice.controller;

import com.example.autoservice.model.Schedule;
import com.example.autoservice.model.User;
import com.example.autoservice.service.EmployeeService;
import com.example.autoservice.service.ScheduleService;
import com.example.autoservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.transaction.annotation.Transactional;


@Controller
@RequiredArgsConstructor
public class CarController {

    private final ScheduleService scheduleService;
    private final EmployeeService employeeService;
    private final UserService userService;

    @GetMapping("/my-cars")
    @Transactional(readOnly = true)
    public String myCars(@AuthenticationPrincipal UserDetails ud, Model m) {
        // достаём email и загружаем пользователя
        String email = ud.getUsername();
        User user = userService.findByEmail(email);
        Long userId = user.getId();

        // проверяем, сотрудник ли это
        var employee = employeeService.getByUserId(userId);
        if (employee != null) {
            // для сотрудника — все его задания
            var employeeSchedules = scheduleService.getByEmployeeId(userId);
            m.addAttribute("employeeSchedules", employeeSchedules);
        } else {
            // для клиента — все его машины в расписании
            var ownerSchedules = scheduleService.getByOwnerId(userId);
            m.addAttribute("ownerSchedules", ownerSchedules);
        }

        return "my_cars";
    }
}
