package com.example.autoservice.mapper;

import com.example.autoservice.dto.CarDTO;
import com.example.autoservice.model.Car;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface CarMapper {
    CarDTO toDto(Car car);
}
