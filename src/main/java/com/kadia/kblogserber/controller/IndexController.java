package com.kadia.kblogserber.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kadia.kblogserber.common.Checker;
import com.kadia.kblogserber.common.JSONfilter;
import com.kadia.kblogserber.common.ResponseData;
import com.kadia.kblogserber.common.ServicEexception;
import com.kadia.kblogserber.entity.Blog;
import com.kadia.kblogserber.service.BlogService;
import com.kadia.kblogserber.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private InfoService infoService;

    @RequestMapping("/getBlog")
    public ResponseData getBlog(@RequestParam(name="page", required = false, defaultValue = "0") Integer page,
                                @RequestParam(name="title", required = false, defaultValue = "") String title,
                                @RequestParam(name="token", required = false, defaultValue = "") String token){
        try{
            boolean isUser = infoService.isUser(token);
            List<Blog> resultList = blogService.getBlog(title,page,isUser);
            JSONArray data = JSONfilter.ListObjectToJSONArrayFilter(resultList,new String[]{"content"});
            return ResponseData.success(data);
        } catch (ServicEexception sExc){
            return ResponseData.error(sExc.getMessage());
        } catch (Exception exc){
            return ResponseData.error("出错啦！请稍后再试");
        }
    }

    @RequestMapping("/viewBlog")
    public Object viewBlog(@RequestParam(name="id", required = false) Integer id,
                           @RequestParam(name="token", required = false) String token){
        try{
            if(Checker.existNull(new Object[]{id})){
                throw new ServicEexception("请求错误，请稍后再试");
            }
            boolean edit = infoService.isUser(token);
            if(!edit){
                blogService.blogViewIncrease(id);
            }
            Blog data = blogService.getBlogInfo(id,edit);
            return ResponseData.success(data);
        } catch (ServicEexception sExc){
            return ResponseData.error(sExc.getMessage());
        } catch (Exception exc){
            return ResponseData.error("出错啦！请稍后再试");
        }
    }
}
