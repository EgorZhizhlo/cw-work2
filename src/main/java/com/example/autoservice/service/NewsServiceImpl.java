package com.example.autoservice.service.impl;

import com.example.autoservice.model.News;
import com.example.autoservice.repository.NewsRepository;
import com.example.autoservice.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepo;

    @Override
    public List<News> getAll() {
        return newsRepo.findAll();
    }

    @Override
    public Optional<News> findById(Long id) {
        return newsRepo.findById(id);
    }
}
