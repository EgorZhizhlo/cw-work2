package com.example.autoservice.service;

import com.example.autoservice.model.WorkingPlace;
import org.springframework.data.domain.Sort;
import java.util.List;

public interface AdminWorkingPlaceService {
    List<WorkingPlace> findAll();
    List<WorkingPlace> findAll(Sort sort);
    WorkingPlace findById(Long id);
    WorkingPlace save(WorkingPlace wp);
    void deleteById(Long id);
}
