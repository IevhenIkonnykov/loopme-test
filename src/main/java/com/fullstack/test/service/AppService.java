package com.fullstack.test.service;

import com.fullstack.test.dto.AppDto;

import java.util.List;

public interface AppService {
    List<AppDto> getAppsByUserId(Long id);

    List<AppDto> findAll();

    void create(AppDto dto) throws IllegalAccessException;

    void update(AppDto dto) throws IllegalAccessException;

    void delete(Long id) throws IllegalAccessException;
}
