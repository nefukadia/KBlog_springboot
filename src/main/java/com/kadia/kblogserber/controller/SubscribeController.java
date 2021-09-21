package com.kadia.kblogserber.controller;

import com.alibaba.fastjson.JSONObject;
import com.kadia.kblogserber.common.Checker;
import com.kadia.kblogserber.common.ResponseData;
import com.kadia.kblogserber.common.ServicEexception;
import com.kadia.kblogserber.entity.Subscribe;
import com.kadia.kblogserber.entity.Verify;
import com.kadia.kblogserber.service.InfoService;
import com.kadia.kblogserber.service.SubscribeService;
import com.kadia.kblogserber.service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.rowset.serial.SerialException;
import java.util.List;

@RestController
@RequestMapping("/subscribe")
public class SubscribeController {
    @Autowired
    private SubscribeService subscribeService;

    @Autowired
    private VerifyService verifyService;

    @Autowired
    private InfoService infoService;

    @PostMapping("/sendVerify")
    public ResponseData sendVerify(@RequestBody JSONObject body){
        try {
            if(Checker.existNullOrEmpty(new Object[]{body.get("email")})){
                throw new ServicEexception("还没完善好信息哩！");
            }
            if(!Checker.isEmail((String) body.get("email"))){
                throw new ServicEexception("请填写正确的邮箱哦！");
            }
            verifyService.sendVerifyAndRecord((String) body.get("email"));
            return ResponseData.success();
        }catch (ServicEexception e){
            return ResponseData.error(e.getMessage());
        }catch (Exception e){
            return ResponseData.error("出错啦！请稍后再试");
        }
    }

    @PostMapping("/doSubscribe")
    public ResponseData doSubscribe(@RequestBody JSONObject body){
        try {
            if(Checker.existNullOrEmpty(new Object[]{body.get("code"),body.get("type")})){
                throw new ServicEexception("还没完善好信息哩！");
            }
            Verify verify = verifyService.verifyAbleAndDelete((String) body.get("code"));
            if(verify == null){
                throw new ServicEexception("验证码不正确哦！");
            }
            if((Integer)body.get("type") == 0){
                if(subscribeService.existSubscribe(verify.getEmail())){
                    throw new ServicEexception("已经订阅过啦！请勿重复订阅");
                }
                subscribeService.saveSubscribe(verify.getEmail());
                return ResponseData.success("订阅成功");
            }else if((Integer)body.get("type") == 1){
                if(!subscribeService.existSubscribe(verify.getEmail())){
                    throw new ServicEexception("还没有订阅过哩");
                }
                subscribeService.deleteSubscribe(verify.getEmail());
                return ResponseData.success("取消订阅成功");
            }else{
                throw new ServicEexception("遇到了点问题，请稍后再试！");
            }
        }catch (ServicEexception e){
            return ResponseData.error(e.getMessage());
        }catch (Exception e){
            return ResponseData.error("出错啦！请稍后再试");
        }
    }

    @RequestMapping("/getList")
    public ResponseData getList(@RequestParam(value = "page",required = false,defaultValue = "0") Integer page,
                                @RequestParam(value = "token") String token){
        try {
            if(!infoService.isUser(token)){
                throw new ServicEexception("身份验证失败！");
            }
            List<Subscribe> data = subscribeService.getList(page);
            return ResponseData.success(data);
        }catch (ServicEexception e){
            return ResponseData.error(e.getMessage());
        }catch (Exception e){
            return ResponseData.error("出错啦！请稍后再试");
        }
    }

    @PostMapping("/deleteSubscribe")
    public ResponseData deleteSubscribe(@RequestBody JSONObject body){
        try {
            if(!infoService.isUser((String) body.get("token"))){
                throw new ServicEexception("身份验证失败！");
            }
            if(Checker.existNullOrEmpty(new Object[]{body.get("email")})){
                throw new ServicEexception("还没完善好信息哩！");
            }
            subscribeService.deleteSubscribe((String) body.get("email"));
            return ResponseData.success("操作成功");
        }catch (ServicEexception e){
            return ResponseData.error(e.getMessage());
        }catch (Exception e){
            return ResponseData.error("出错啦！请稍后再试");
        }
    }
}
