package com.example.autoservice.service;

import com.example.autoservice.model.Car;
import java.util.List;

public interface CarService {
    List<Car> getByOwnerId(Long userId);
    List<Car> findAll();
    long count();

}
