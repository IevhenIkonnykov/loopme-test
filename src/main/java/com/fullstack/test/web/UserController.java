package com.fullstack.test.web;

import com.fullstack.test.dto.UserDto;
import com.fullstack.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping(value = {"/api/publisher", "/api/operator"}, method = RequestMethod.POST)
    public void createPublisher(@RequestBody UserDto dto, HttpServletResponse response) {
        try {
            userService.create(dto);
        } catch (IllegalAccessException e) {
            response.setStatus(403);
        }
    }

    @RequestMapping(value = {"/api/publisher", "/api/operator"}, method = RequestMethod.PUT)
    public void updatePublisher(@RequestBody UserDto dto, HttpServletResponse response) {
        try {
            userService.update(dto);
        } catch (IllegalAccessException e) {
            response.setStatus(403);
        }
    }

    @RequestMapping(value = {"/api/publisher/{id}", "/api/operator/{id}"}, method = RequestMethod.DELETE)
    public void deletePublisher(@PathVariable Long id, HttpServletResponse response) {
        try {
            userService.delete(id);
        } catch (IllegalAccessException e) {
            response.setStatus(403);
        }
    }
}
