package com.kadia.kblogserber.service.serviceImp;

import com.kadia.kblogserber.common.ServicEexception;
import com.kadia.kblogserber.common.Timer;
import com.kadia.kblogserber.entity.Message;
import com.kadia.kblogserber.mapper.MessageMapper;
import com.kadia.kblogserber.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImp implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public List<Message> getMessage(Integer page) throws ServicEexception {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("time").descending());
        return messageMapper.findAll(pageable).getContent();
    }

    @Override
    public void sendMessage(String email, String nickname, String context) throws ServicEexception {
        Message m = new Message();
        m.setEmail(email);
        m.setNickname(nickname);
        m.setContext(context);
        m.setTime(Timer.getTime());
        messageMapper.save(m);
    }

    @Override
    public void deleteMessage(Integer id) throws ServicEexception {
        Message message = messageMapper.getById(id);
        messageMapper.delete(message);
    }
}
