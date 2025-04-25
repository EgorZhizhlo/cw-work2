package com.example.autoservice.service;

import com.example.autoservice.model.Car;
import com.example.autoservice.repository.CarRepository;
import com.example.autoservice.repository.ScheduleRepository;
import com.example.autoservice.service.AdminCarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCarServiceImpl implements AdminCarService {
    private final CarRepository repo;
    private final ScheduleRepository scheduleRepository;

    @Override
    public List<Car> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Car> findAll(Sort sort) {
        return repo.findAll(sort);
    }

    @Override
    public Car findById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    @Override
    public Car save(Car car) {
        return repo.save(car);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        scheduleRepository.deleteByCarId(id);
        repo.deleteById(id);
    }
}
