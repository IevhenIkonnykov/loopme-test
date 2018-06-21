package com.fullstack.test.web;

import com.fullstack.test.dto.UserDto;
import com.fullstack.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    public UserDto getUser() {

        return userService.getUser();
    }

    @RequestMapping(value = "/api/publisher", method = RequestMethod.GET)
    public List<UserDto> getPublishers() {

        return userService.getPublishers();
    }

    @RequestMapping(value = "/api/operator", method = RequestMethod.GET)
    public List<UserDto> getOperators() {

        return userService.getOperators();
    }
}
