package com.kadia.kblogserber.service;

import com.kadia.kblogserber.common.ServicEexception;
import com.kadia.kblogserber.entity.Links;

import java.util.List;

public interface LinksService {
    List<Links> getLinks() throws ServicEexception;
    void changLink(Integer id, String avatar, String nickname, String url) throws  ServicEexception;
    void deleteLink(Integer id) throws  ServicEexception;
}
