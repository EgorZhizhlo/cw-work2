package com.example.autoservice.service;

import com.example.autoservice.model.Car;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface AdminCarService {
    List<Car> findAll();
    // новый метод
    List<Car> findAll(Sort sort);
    Car findById(Long id);
    Car save(Car car);
    void deleteById(Long id);
}
