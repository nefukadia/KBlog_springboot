package com.kadia.kblogserber.mapper;

import com.kadia.kblogserber.entity.Subscribe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SubscribeMapper extends JpaRepository<Subscribe, Integer> {
    Subscribe findOneByEmail(String email);
    @Transactional
    void deleteByEmail(String email);
    List<Subscribe> findAll();
    Page<Subscribe> findAll(Pageable pageable);
}

