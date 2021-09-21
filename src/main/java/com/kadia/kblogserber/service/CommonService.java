package com.kadia.kblogserber.service;

import com.kadia.kblogserber.common.ServicEexception;

public interface CommonService {
    void sendMail(String to, String subject, String content) throws ServicEexception;
}
