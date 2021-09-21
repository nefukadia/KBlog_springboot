package com.kadia.kblogserber.service;

import com.kadia.kblogserber.common.ServicEexception;
import com.kadia.kblogserber.entity.Subscribe;

import java.util.List;

public interface SubscribeService {
    long count() throws ServicEexception;
    boolean existSubscribe(String email) throws ServicEexception;
    void saveSubscribe(String email) throws ServicEexception;
    void deleteSubscribe(String email) throws ServicEexception;
    List<Subscribe> getList(Integer page) throws ServicEexception;
}

