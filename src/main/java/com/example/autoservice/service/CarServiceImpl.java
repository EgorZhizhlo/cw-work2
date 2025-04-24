package com.example.autoservice.service.impl;

import com.example.autoservice.model.Car;
import com.example.autoservice.repository.CarRepository;
import com.example.autoservice.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository repo;

    @Override
    public List<Car> getByOwnerId(Long userId) {
        return repo.findByOwnerId(userId);
    }

    @Override
    public List<Car> findAll() {
        return repo.findAll();
    }

    @Override public long count() {
        return repo.count();
    }
}

