package com.kadia.kblogserber.service.serviceImp;

import com.kadia.kblogserber.common.ServicEexception;
import com.kadia.kblogserber.entity.Links;
import com.kadia.kblogserber.mapper.LinksMapper;
import com.kadia.kblogserber.service.LinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinksServiceImp implements LinksService {
    @Autowired
    private LinksMapper linksMapper;

    @Override
    public List<Links> getLinks() throws ServicEexception {
        return linksMapper.findAll();
    }

    @Override
    public void changLink(Integer id, String avatar, String nickname, String url) throws ServicEexception {
        Links links = new Links();
        if(id != null){
            links = linksMapper.getById(id);
        }
        links.setId(id);
        links.setAvatar(avatar);
        links.setNickname(nickname);
        links.setUrl(url);
        linksMapper.save(links);
    }

    @Override
    public void deleteLink(Integer id) throws ServicEexception {
        Links links = linksMapper.getById(id);
        linksMapper.delete(links);
    }
}
