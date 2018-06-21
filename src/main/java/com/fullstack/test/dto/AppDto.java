package com.fullstack.test.dto;

import com.fullstack.test.domain.AppType;
import com.fullstack.test.domain.ContentType;

import java.util.Set;

public class AppDto {

    private Long id;
    private String name;
    private AppType type;
    private Set<ContentType> contentTypes;
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AppType getType() {
        return type;
    }

    public void setType(AppType type) {
        this.type = type;
    }

    public Set<ContentType> getContentTypes() {
        return contentTypes;
    }

    public void setContentTypes(Set<ContentType> contentTypes) {
        this.contentTypes = contentTypes;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
