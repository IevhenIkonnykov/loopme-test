package com.fullstack.test.service;

import com.fullstack.test.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUser();

    List<UserDto> getPublishers();

    List<UserDto> getOperators();

    void create(UserDto app) throws IllegalAccessException;

    void update(UserDto app) throws IllegalAccessException;

    void delete(Long id) throws IllegalAccessException;
}
