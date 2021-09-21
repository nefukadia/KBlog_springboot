package com.kadia.kblogserber.mapper;

import com.kadia.kblogserber.entity.Verify;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerifyMapper extends JpaRepository<Verify,Integer> {
    Verify findOneByVerifyAndTimeGreaterThan(String verify,long time);
}
