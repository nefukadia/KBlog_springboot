package com.kadia.kblogserber.service.serviceImp;

import com.kadia.kblogserber.common.ServicEexception;
import com.kadia.kblogserber.entity.Subscribe;
import com.kadia.kblogserber.mapper.SubscribeMapper;
import com.kadia.kblogserber.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscribeServiceImp implements SubscribeService {
    @Autowired
    private SubscribeMapper subscribeMapper;

    @Override
    public long count() throws ServicEexception {
        return subscribeMapper.count();
    }

    @Override
    public boolean existSubscribe(String email) throws ServicEexception{
        return subscribeMapper.findOneByEmail(email) != null;
    }

    @Override
    public void saveSubscribe(String email) throws ServicEexception {
        Subscribe subscribe = new Subscribe();
        subscribe.setEmail(email);
        subscribeMapper.save(subscribe);
    }

    @Override
    public void deleteSubscribe(String email) throws ServicEexception {
        subscribeMapper.deleteByEmail(email);
    }

    @Override
    public List<Subscribe> getList(Integer page) throws ServicEexception {
        Pageable pageable = PageRequest.of(page, 10);
        return subscribeMapper.findAll(pageable).getContent();
    }
}

