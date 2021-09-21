package com.kadia.kblogserber.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kadia.kblogserber.common.Checker;
import com.kadia.kblogserber.common.JSONfilter;
import com.kadia.kblogserber.common.ResponseData;
import com.kadia.kblogserber.common.ServicEexception;
import com.kadia.kblogserber.entity.Links;
import com.kadia.kblogserber.service.InfoService;
import com.kadia.kblogserber.service.LinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/links")
public class LinksController {
    @Autowired
    private LinksService linksService;

    @Autowired
    private InfoService infoService;

    @RequestMapping("/getLinks")
    public ResponseData getBlog(){
        try{
            List<Links> resultList = linksService.getLinks();
            JSONArray data = JSONfilter.ListObjectToJSONArrayFilter(resultList,new String[]{});
            return ResponseData.success(data);
        } catch (ServicEexception sExc){
            return ResponseData.error(sExc.getMessage());
        } catch (Exception exc){
            return ResponseData.error("出错啦！请稍后再试");
        }
    }

    @PostMapping("/deleteLink")
    public ResponseData deleteLink(@RequestBody JSONObject body){
        try{
            if(!infoService.isUser((String) body.get("token"))){
                throw new ServicEexception("身份验证失败！");
            }
            if(Checker.existNullOrEmpty(new Object[]{body.get("id")})){
                throw new ServicEexception("请先完善信息哦！");
            }
            linksService.deleteLink((Integer) body.get("id"));
            return ResponseData.success("操作成功");
        } catch (ServicEexception sExc){
            return ResponseData.error(sExc.getMessage());
        } catch (Exception exc){
            return ResponseData.error("出错啦！请稍后再试");
        }
    }

    @PostMapping("/changLink")
    public ResponseData changLink(@RequestBody JSONObject body){
        try{
            if(!infoService.isUser((String) body.get("token"))){
                throw new ServicEexception("身份验证失败！");
            }
            if(Checker.existNullOrEmpty(new Object[]{
                    body.get("avatar"),
                    body.get("nickname"),
                    body.get("url")
            })){
                throw new ServicEexception("请先完善信息哦！");
            }
            linksService.changLink((Integer) body.get("id"),(String) body.get("avatar"),(String) body.get("nickname"), (String) body.get("url"));
            return ResponseData.success("操作成功");
        } catch (ServicEexception sExc){
            return ResponseData.error(sExc.getMessage());
        } catch (Exception exc){
            return ResponseData.error("出错啦！请稍后再试");
        }
    }
}
