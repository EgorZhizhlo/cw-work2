package com.example.autoservice.service;

import com.example.autoservice.model.News;
import java.util.List;
import java.util.Optional;

public interface NewsService {
    List<News> getAll();
    Optional<News> findById(Long id);
}
