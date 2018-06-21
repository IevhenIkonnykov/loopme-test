package com.fullstack.test.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "apps")
public class App {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AppType type;

    @Column(nullable = false)
    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE})
    private Set<ContentType> contentTypes;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof App)) return false;
        App app = (App) o;
        return Objects.equals(getId(), app.getId()) &&
                Objects.equals(getName(), app.getName()) &&
                getType() == app.getType() &&
                Objects.equals(getContentTypes(), app.getContentTypes());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getName(), getType(), getContentTypes());
    }
}
