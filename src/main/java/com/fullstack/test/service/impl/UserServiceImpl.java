package com.fullstack.test.service.impl;

import com.fullstack.test.dao.UserRepository;
import com.fullstack.test.domain.User;
import com.fullstack.test.domain.UserPrincipal;
import com.fullstack.test.domain.UserRole;
import com.fullstack.test.dto.UserDto;
import com.fullstack.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto getUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        UserPrincipal userPrincipal = (UserPrincipal) (context.getAuthentication().getPrincipal());

        UserDto userDto = new UserDto();
        userDto.setId(userPrincipal.getUser().getId());
        userDto.setName(userPrincipal.getUser().getName());
        userDto.setEmail(userPrincipal.getUser().getEmail());
        userDto.setAuthorities(userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        return userDto;
    }

    @Override
    public List<UserDto> getPublishers() {
        return getUserDtos(UserRole.PUBLISHER);
    }

    @Override
    public List<UserDto> getOperators() {
        return getUserDtos(UserRole.ADOPS);
    }

    @Override
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    private List<UserDto> getUserDtos(UserRole role) {
        return userRepository.findByRole(role)
                .stream()
                .map(user -> {
                    UserDto userDto = new UserDto();
                    userDto.setName(user.getName());
                    userDto.setEmail(user.getEmail());
                    userDto.setAuthorities(Arrays.asList(user.getRole().name()));

                    return userDto;
                })
                .collect(Collectors.toList());
    }
}
