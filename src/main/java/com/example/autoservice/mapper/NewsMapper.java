package com.example.autoservice.mapper;

import com.example.autoservice.dto.NewsDTO;
import com.example.autoservice.model.News;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NewsMapper {
    NewsDTO toDto(News news);
}
