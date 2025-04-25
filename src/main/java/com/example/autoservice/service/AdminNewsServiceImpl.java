package com.example.autoservice.service;

import com.example.autoservice.model.News;
import com.example.autoservice.repository.NewsRepository;
import com.example.autoservice.service.AdminNewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminNewsServiceImpl implements AdminNewsService {
    private final NewsRepository repo;

    @Override public List<News> findAll()               {
        return repo.findAll();
    }
    @Override public List<News> findAll(Sort sort)      {
        return repo.findAll(sort);
    }
    @Override public News findById(Long id)             {
        return repo.findById(id).orElseThrow();
    }
    @Override public News save(News n)                  {
        return repo.save(n);
    }
    @Override public void deleteById(Long id)           {
        repo.deleteById(id);
    }
}
