package com.kadia.kblogserber.service.serviceImp;

import com.kadia.kblogserber.common.RandomString;
import com.kadia.kblogserber.common.ServicEexception;
import com.kadia.kblogserber.entity.Info;
import com.kadia.kblogserber.mapper.InfoMapper;
import com.kadia.kblogserber.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoServiceImp implements InfoService {
    @Autowired
    private InfoMapper infoMapper;

    @Override
    public Info getInfo() throws ServicEexception {
        return infoMapper.getById(1);
    }

    @Override
    public Boolean isUser(String token) throws ServicEexception {
        Info info = infoMapper.getById(1);
        return info.getToken().equals(token);
    }

    @Override
    public String change(String name, String password, String email, String notice, String about) throws ServicEexception {
        Info info = infoMapper.getById(1);
        if(name != null)
            info.setName(name);
        if(password != null)
            info.setPassword(password);
        if(email != null)
            info.setEmail(email);
        if(notice != null)
            info.setNotice(notice);
        if(about != null)
            info.setAbout(about);
        String token = RandomString.getCharAndNum(12);
        info.setToken(token);
        infoMapper.save(info);
        return token;
    }

    @Override
    public void setAvatar(String avatar) throws ServicEexception {
        Info info = infoMapper.getById(1);
        info.setAvatar(avatar);
        infoMapper.save(info);
    }

    @Override
    public String login(String name, String password) throws ServicEexception {
        Info info = infoMapper.getById(1);
        if(info.getName().equals(name)&&info.getPassword().equals(password)){
            String token = RandomString.getCharAndNum(12);
            info.setToken(token);
            infoMapper.save(info);
            return token;
        }else{
            return null;
        }
    }
}
