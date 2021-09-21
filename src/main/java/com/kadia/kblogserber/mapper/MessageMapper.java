package com.kadia.kblogserber.mapper;

import com.kadia.kblogserber.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageMapper extends JpaRepository<Message, Integer> {

}

