package com.fullstack.test.dao;

import com.fullstack.test.domain.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AppRepository extends JpaRepository<App, Long> {
    List<App> findAllByUserId(Long id);
}
