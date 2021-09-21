package com.kadia.kblogserber.service;

import com.kadia.kblogserber.common.ServicEexception;
import com.kadia.kblogserber.entity.Message;

import java.util.List;

public interface MessageService {
    List<Message> getMessage(Integer page) throws ServicEexception;
    void sendMessage(String email, String nickname, String context) throws ServicEexception;
    void deleteMessage(Integer id) throws ServicEexception;
}
