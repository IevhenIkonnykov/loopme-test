package com.fullstack.test.web;

import com.fullstack.test.domain.UserPrincipal;
import com.fullstack.test.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class LoginController {
    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    public UserDto getUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        UserPrincipal userPrincipal = (UserPrincipal) (context.getAuthentication().getPrincipal());

        UserDto userDto = new UserDto();
        userDto.setName(userPrincipal.getUser().getName());
        userDto.setEmail(userPrincipal.getUser().getEmail());
        userDto.setAuthorities(userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        return userDto;
    }
}
