package com.fullstack.test.service;

import com.fullstack.test.domain.App;
import com.fullstack.test.dto.AppDto;

import java.util.List;

public interface AppService {
    List<AppDto> getAppsByUserId(Long id);
    List<AppDto> findAll();
    void create(App app);
    void update(App app);
    void delete(App app);
}
