package com.example.autoservice.service;

import com.example.autoservice.model.News;
import org.springframework.data.domain.Sort;
import java.util.List;

public interface AdminNewsService {
    List<News> findAll();
    List<News> findAll(Sort sort);
    News findById(Long id);
    News save(News news);
    void deleteById(Long id);
}
