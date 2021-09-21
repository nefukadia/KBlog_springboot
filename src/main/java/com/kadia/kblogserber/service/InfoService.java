package com.kadia.kblogserber.service;

import com.kadia.kblogserber.common.ServicEexception;
import com.kadia.kblogserber.entity.Info;

public interface InfoService {
    Info getInfo() throws ServicEexception;
    Boolean isUser(String token) throws ServicEexception;
    String change(String name, String password, String email, String notice, String about) throws ServicEexception;
    void setAvatar(String avatar) throws ServicEexception;
    String login(String name, String password) throws ServicEexception;
}
