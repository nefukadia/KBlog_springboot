package com.kadia.kblogserber.service;

import com.kadia.kblogserber.common.ServicEexception;
import com.kadia.kblogserber.entity.Blog;
import java.util.List;

public interface BlogService {
    List<Blog> getBlog(String title, Integer page, Boolean isUser) throws ServicEexception;
    Blog getBlogInfo(Integer id, Boolean edit) throws ServicEexception;
    void blogViewIncrease(Integer id) throws ServicEexception;
    long countOpenBlog() throws ServicEexception;
    long sumView() throws ServicEexception;
    void doBlog(Integer id,String title, String content) throws ServicEexception;
    void changeBlog(Integer id, Integer type) throws ServicEexception;
}
