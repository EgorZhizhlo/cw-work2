package com.example.autoservice.service;

import com.example.autoservice.model.WorkingPlace;
import com.example.autoservice.repository.WorkingPlaceRepository;
import com.example.autoservice.service.AdminWorkingPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminWorkingPlaceServiceImpl implements AdminWorkingPlaceService {
    private final WorkingPlaceRepository repo;

    @Override public List<WorkingPlace> findAll()                { return repo.findAll(); }
    @Override public List<WorkingPlace> findAll(Sort sort)       { return repo.findAll(sort); }
    @Override public WorkingPlace findById(Long id)             { return repo.findById(id).orElseThrow(); }
    @Override public WorkingPlace save(WorkingPlace w)          { return repo.save(w); }
    @Override public void deleteById(Long id)                   { repo.deleteById(id); }
}
