package com.fullstack.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/api/login").permitAll()
                .antMatchers("/api/app").access("hasAnyRole('PUBLISHER', 'ADOPS')")
                .antMatchers("/api/publisher").access("hasAnyRole('ADMIN', 'ADOPS')")
                .antMatchers("/api/operator").access("hasRole('ADMIN')");
    }
}
