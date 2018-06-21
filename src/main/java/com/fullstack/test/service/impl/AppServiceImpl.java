package com.fullstack.test.service.impl;

import com.fullstack.test.dao.AppRepository;
import com.fullstack.test.domain.App;
import com.fullstack.test.dto.AppDto;
import com.fullstack.test.service.AppService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppServiceImpl implements AppService {

    private final AppRepository appRepository;

    @Autowired
    public AppServiceImpl(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @Override
    public List<AppDto> getAppsByUserId(Long id) {
        return this.appRepository.findAllByUserId(id)
                .stream()
                .map(app -> {
                    AppDto dto = new AppDto();
                    BeanUtils.copyProperties(app, dto);
                    dto.setUserId(app.getUser().getId());

                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public List<AppDto> findAll() {
        return this.appRepository.findAll()
                .stream()
                .map(app -> {
                    AppDto dto = new AppDto();
                    BeanUtils.copyProperties(app, dto);
                    dto.setUserId(app.getUser().getId());

                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public void create(App app) {
        this.appRepository.save(app);
    }

    @Override
    public void update(App app) {
        this.appRepository.save(app);
    }

    @Override
    public void delete(App app) {
        this.appRepository.delete(app);
    }
}
