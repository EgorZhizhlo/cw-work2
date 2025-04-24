package com.example.autoservice.mapper;

import com.example.autoservice.dto.EmployeeDTO;
import com.example.autoservice.model.EmployeeBase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface EmployeeMapper {
    EmployeeDTO toDto(EmployeeBase emp);
}
