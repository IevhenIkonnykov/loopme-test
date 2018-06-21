package com.fullstack.test.service.impl;

import com.fullstack.test.dao.AppRepository;
import com.fullstack.test.dao.UserRepository;
import com.fullstack.test.domain.App;
import com.fullstack.test.domain.User;
import com.fullstack.test.domain.UserPrincipal;
import com.fullstack.test.domain.UserRole;
import com.fullstack.test.dto.AppDto;
import com.fullstack.test.service.AppService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppServiceImpl implements AppService {

    private final AppRepository appRepository;
    private final UserRepository userRepository;

    @Autowired
    public AppServiceImpl(AppRepository appRepository, UserRepository userRepository) {
        this.appRepository = appRepository;
        this.userRepository = userRepository;
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
    public void create(AppDto dto) throws IllegalAccessException {
        if(dto.getId() != null){
            throw new IllegalAccessException("App id should not be set.");
        }

        App app = new App();
        BeanUtils.copyProperties(dto, app);
        Optional<User> user = this.userRepository.findById(dto.getUserId());

        if (user.isPresent()) {
            this.validateAppAccess(user.get().getId());
            app.setUser(user.get());
            this.appRepository.save(app);
        }
    }

    @Override
    public void update(AppDto dto) throws IllegalAccessException {
        Optional<App> appOptional = this.appRepository.findById(dto.getId());

        if (appOptional.isPresent()) {
            App app = appOptional.get();
            this.validateAppAccess(app.getUser().getId());
            BeanUtils.copyProperties(dto, app);
            this.appRepository.save(app);
        }
    }

    @Override
    public void delete(Long id) throws IllegalAccessException {
        Optional<App> appOptional = this.appRepository.findById(id);

        if (appOptional.isPresent()) {
            App app = appOptional.get();
            this.validateAppAccess(app.getUser().getId());
            this.appRepository.delete(app);
        }
    }

    private void validateAppAccess(Long id) throws IllegalAccessException {
        SecurityContext context = SecurityContextHolder.getContext();
        UserPrincipal userPrincipal = (UserPrincipal) (context.getAuthentication().getPrincipal());

        if (userPrincipal.getAuthorities().stream().filter(x -> x.getAuthority().equals(UserRole.ADOPS.name())).collect(Collectors.toList()).size() == 0 && !userPrincipal.getUser().getId().equals(id)) {
            throw new IllegalAccessException("You are not allowed to delete someone's app.");
        }
    }
}
