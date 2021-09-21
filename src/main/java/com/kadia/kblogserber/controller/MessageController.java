package com.kadia.kblogserber.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kadia.kblogserber.common.Checker;
import com.kadia.kblogserber.common.JSONfilter;
import com.kadia.kblogserber.common.ResponseData;
import com.kadia.kblogserber.common.ServicEexception;
import com.kadia.kblogserber.service.InfoService;
import com.kadia.kblogserber.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.kadia.kblogserber.entity.Message;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private InfoService infoService;

    @RequestMapping("/getMessage")
    public ResponseData getMessage(@RequestParam(name="page", required = false, defaultValue = "0") Integer page){
        try{
            List<Message> resultList = messageService.getMessage(page);
            JSONArray data = JSONfilter.ListObjectToJSONArrayFilter(resultList,new String[]{});
            return ResponseData.success(data);
        } catch (ServicEexception sExc){
            return ResponseData.error(sExc.getMessage());
        } catch (Exception exc){
            return ResponseData.error("出错啦！请稍后再试");
        }
    }

    @PostMapping("/sendMessage")
    public ResponseData sendMessage(@RequestBody JSONObject body){
        try{
            if(Checker.existNullOrEmpty(new Object[]{
                    body.get("email"),
                    body.get("nickname"),
                    body.get("context")})){
                throw new ServicEexception("还没完善好信息哩！");
            }
            if(!Checker.isEmail((String) body.get("email"))){
                throw new ServicEexception("邮箱格式不太对哦！");
            }
            messageService.sendMessage((String) body.get("email"),(String)body.get("nickname"),(String)body.get("context"));
            return ResponseData.success();
        } catch (ServicEexception sExc){
            return ResponseData.error(sExc.getMessage());
        }
        catch (Exception exc){
            return ResponseData.error("出错啦！请稍后再试");
        }
    }

    @PostMapping("/deleteMessage")
    public ResponseData deleteMessage(@RequestBody JSONObject body){
        try{
            if(!infoService.isUser((String) body.get("token"))){
                throw new ServicEexception("身份验证失败！");
            }
            if(Checker.existNullOrEmpty(new Object[]{body.get("id")})){
                throw new ServicEexception("还没完善好信息哩！");
            }
            messageService.deleteMessage((Integer) body.get("id"));
            return ResponseData.success("操作成功");
        } catch (ServicEexception sExc){
            return ResponseData.error(sExc.getMessage());
        }
        catch (Exception exc){
            return ResponseData.error("出错啦！请稍后再试");
        }
    }
}
