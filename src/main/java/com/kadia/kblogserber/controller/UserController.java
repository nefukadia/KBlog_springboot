package com.kadia.kblogserber.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kadia.kblogserber.common.*;
import com.kadia.kblogserber.entity.Info;
import com.kadia.kblogserber.service.BlogService;
import com.kadia.kblogserber.service.InfoService;
import com.kadia.kblogserber.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private InfoService infoService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private SubscribeService subscribeService;

    @RequestMapping("/getInfo")
    public ResponseData getInfo(@RequestBody(required = false) JSONObject body){
        try{
            Info result = infoService.getInfo();
            JSONObject data = null;
            if(body == null||!infoService.isUser((String) body.get("token"))){
                data = JSONfilter.ObjectToJSONObjectFilter(result,new String[]{
                    "id",
                    "password",
                    "token"
                });
            }else{
                data = (JSONObject) JSON.toJSON(result);
            }
            return ResponseData.success(data);
        } catch (ServicEexception sExc){
            return ResponseData.error(sExc.getMessage());
        } catch (Exception exc){
            return ResponseData.error("出错啦！请稍后再试");
        }
    }

    @RequestMapping("/getStatistics")
    public ResponseData getStatistics(){
        try{
            JSONObject data = new JSONObject();
            data.put("blog",blogService.countOpenBlog());
            data.put("subscribe",subscribeService.count());
            data.put("view",blogService.sumView());
            return ResponseData.success(data);
        } catch (ServicEexception sExc){
            return ResponseData.error(sExc.getMessage());
        } catch (Exception exc){
            return ResponseData.error("出错啦！请稍后再试");
        }
    }

    @PostMapping("/doBlog")
    public ResponseData doBlog(@RequestBody JSONObject body){
        try{
            if(!infoService.isUser((String) body.get("token"))){
                throw new ServicEexception("身份验证失败！");
            }
            if(Checker.existNullOrEmpty(new Object[]{body.get("title"),body.get("content")})){
                throw new ServicEexception("还没完善好信息哩！");
            }
            blogService.doBlog((Integer) body.get("id"), (String) body.get("title"),(String) body.get("content"));
            return ResponseData.success();
        } catch (ServicEexception sExc){
            return ResponseData.error(sExc.getMessage());
        } catch (Exception exc){
            return ResponseData.error("出错啦！请稍后再试");
        }
    }

    @PostMapping("/changeInfo")
    public ResponseData changeInfo(@RequestBody JSONObject body){
        try{
            if(!infoService.isUser((String) body.get("token"))){
                throw new ServicEexception("身份验证失败！");
            }
            String token = null ;
            if(body.get("type").equals(0)){
                if (Checker.existNullOrEmpty(new Object[]{body.get("name"), body.get("password"), body.get("email")})) {
                    throw new ServicEexception("请先完善信息哦！");
                }else{
                    token = infoService.change((String) body.get("name"),
                                        (String) body.get("password"),
                                        (String) body.get("email"),
                                        null,null);
                }
            }else if(body.get("type").equals(1)){
                if (Checker.existNullOrEmpty(new Object[]{body.get("notice")})){
                    throw new ServicEexception("请先完善信息哦！");
                }else{
                    token = infoService.change(null,null,null,(String) body.get("notice"),null);
                }
            }else if(body.get("type").equals(2)){
                if (Checker.existNullOrEmpty(new Object[]{body.get("about")})){
                    throw new ServicEexception("请先完善信息哦！");
                }else{
                    token = infoService.change(null,null,null,null,(String) body.get("about"));
                }
            }else{
                throw new ServicEexception("请求错误！请稍后再试");
            }
            return ResponseData.success(token);
        } catch (ServicEexception sExc){
            return ResponseData.error(sExc.getMessage());
        } catch (Exception exc){
            return ResponseData.error("出错啦！请稍后再试");
        }
    }

    @PostMapping(value="/upload")
    public ResponseData upload(@RequestParam("file") MultipartFile file, @RequestParam("token") String token) {
        try {
            if (!infoService.isUser(token)) {
                throw new ServicEexception("身份验证失败！");
            }
            if (file.isEmpty()) {
                return ResponseData.error("请选择文件");
            }
            String fileName = file.getOriginalFilename();
            String filePath = System.getProperty("user.dir")+"/upload/";
            File tmp = new File(filePath);
            if (!tmp.exists()) {
                if (!tmp.mkdir()) {
                    throw new ServicEexception("出错啦！");
                }
            }
            String randString = Timer.getTime() + RandomString.getCharAndNum(8);
            File dest = new File(filePath + randString + fileName);
            file.transferTo(dest);
            String vueUrl="/upload/"+randString + fileName;
            return ResponseData.success(vueUrl);
        } catch (ServicEexception sExc) {
            return ResponseData.error(sExc.getMessage());
        } catch (Exception e) {
            return ResponseData.error("出错啦！请稍后再试");
        }
    }

    @PostMapping("/changeBlog")
    public ResponseData changeBlog(@RequestBody JSONObject body){
        try{
            if(!infoService.isUser((String) body.get("token"))){
                throw new ServicEexception("身份验证失败！");
            }
            if(Checker.existNullOrEmpty(new Object[]{body.get("id"),body.get("type")})){
                throw new ServicEexception("还没完善好信息哩！");
            }
            if(body.get("type").equals(0)||body.get("type").equals(1)){
                blogService.changeBlog((Integer) body.get("id"),(Integer) body.get("type"));
                return ResponseData.success("操作成功");
            }else{
                return ResponseData.error("请求错误,请稍后再试");
            }
        } catch (ServicEexception sExc){
            return ResponseData.error(sExc.getMessage());
        } catch (Exception exc){
            return ResponseData.error("出错啦！请稍后再试");
        }
    }

    @PostMapping("/login")
    public ResponseData login(@RequestBody JSONObject body){
        try{
            if(Checker.existNullOrEmpty(new Object[]{body.get("name"),body.get("password")})){
                throw new ServicEexception("还没完善好信息哩！");
            }
            String token = infoService.login((String) body.get("name"), (String) body.get("password"));
            if(token == null)
                return ResponseData.error("账号或密码错误！");
            return ResponseData.success(token);
        } catch (ServicEexception sExc){
            return ResponseData.error(sExc.getMessage());
        } catch (Exception exc){
            return ResponseData.error("出错啦！请稍后再试");
        }
    }

    @PostMapping("/setAvatar")
    public ResponseData setAvatar(@RequestBody JSONObject body){
        try{
            if(!infoService.isUser((String) body.get("token"))){
                throw new ServicEexception("身份验证失败！");
            }
            if(Checker.existNullOrEmpty(new Object[]{body.get("avatar")})){
                throw new ServicEexception("还没完善好信息哩！");
            }
            infoService.setAvatar((String) body.get("avatar"));
            return ResponseData.success();
        } catch (ServicEexception sExc){
            return ResponseData.error(sExc.getMessage());
        } catch (Exception exc){
            return ResponseData.error("出错啦！请稍后再试");
        }
    }

    @PostMapping("/checkToken")
    public ResponseData checkToken(@RequestBody JSONObject body){
        try{
            if(!infoService.isUser((String) body.get("token"))){
                throw new ServicEexception("身份验证失败！");
            }
            return ResponseData.success();
        } catch (ServicEexception sExc){
            return ResponseData.error(sExc.getMessage());
        } catch (Exception exc){
            return ResponseData.error("出错啦！请稍后再试");
        }
    }
}
