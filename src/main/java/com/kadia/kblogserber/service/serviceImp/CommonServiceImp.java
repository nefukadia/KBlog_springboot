package com.kadia.kblogserber.service.serviceImp;

import com.kadia.kblogserber.common.ServicEexception;
import com.kadia.kblogserber.entity.Info;
import com.kadia.kblogserber.mapper.InfoMapper;
import com.kadia.kblogserber.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;


@Service
public class CommonServiceImp implements CommonService {

    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private Environment env;

    @Resource
    private InfoMapper infoMapper;

    @Override
    @Async
    @Transactional
    public void sendMail(String to, String subject, String content) throws ServicEexception {
        String from = env.getProperty("spring.mail.username");
        Info info = infoMapper.getById(1);
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(info.getName() + "<" + (from != null ? from : "") + ">");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            javaMailSender.send(message);
        }catch (Exception e){
            throw new ServicEexception("邮件发送失败,请稍后再试");
        }
    }
}
