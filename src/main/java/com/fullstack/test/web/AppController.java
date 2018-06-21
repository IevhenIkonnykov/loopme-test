package com.fullstack.test.web;

import com.fullstack.test.domain.UserPrincipal;
import com.fullstack.test.domain.UserRole;
import com.fullstack.test.dto.AppDto;
import com.fullstack.test.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AppController {

    private final AppService appService;

    @Autowired
    public AppController(AppService appService) {
        this.appService = appService;
    }

    @RequestMapping(value = "/api/app", method = RequestMethod.GET)
    public List<AppDto> getApps() {
        SecurityContext context = SecurityContextHolder.getContext();
        UserPrincipal userPrincipal = (UserPrincipal) (context.getAuthentication().getPrincipal());

        if (userPrincipal.getAuthorities().stream().filter(x -> x.getAuthority().equals(UserRole.ADOPS.name())).collect(Collectors.toList()).size() > 0) {
            return appService.findAll();
        }

        return appService.getAppsByUserId(userPrincipal.getUser().getId());
    }

    @RequestMapping(value = "/api/app", method = RequestMethod.POST)
    public void createApp(@RequestBody AppDto dto, HttpServletResponse response) {
        try {
            appService.create(dto);
        } catch (IllegalAccessException e) {
            response.setStatus(403);
        }
    }

    @RequestMapping(value = "/api/app", method = RequestMethod.PUT)
    public void updateApp(@RequestBody AppDto dto, HttpServletResponse response) {
        try {
            appService.update(dto);
        } catch (IllegalAccessException e) {
            response.setStatus(403);
        }
    }

    @RequestMapping(value = "/api/app/{id}", method = RequestMethod.DELETE)
    public void deleteApp(@PathVariable Long id, HttpServletResponse response) {
        try {
            appService.delete(id);
        } catch (IllegalAccessException e) {
            response.setStatus(403);
        }
    }
}
