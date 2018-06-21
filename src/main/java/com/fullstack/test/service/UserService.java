package com.fullstack.test.service;

import com.fullstack.test.domain.User;
import com.fullstack.test.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUser();
    List<UserDto> getPublishers();
    List<UserDto> getOperators();
    void create(User app);
    void update(User app);
    void delete(User app);
}
