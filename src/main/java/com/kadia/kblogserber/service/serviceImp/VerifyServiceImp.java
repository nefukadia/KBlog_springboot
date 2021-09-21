package com.kadia.kblogserber.service.serviceImp;

import com.kadia.kblogserber.common.MailHtml;
import com.kadia.kblogserber.common.RandomString;
import com.kadia.kblogserber.common.ServicEexception;
import com.kadia.kblogserber.common.Timer;
import com.kadia.kblogserber.entity.Info;
import com.kadia.kblogserber.entity.Verify;
import com.kadia.kblogserber.mapper.InfoMapper;
import com.kadia.kblogserber.mapper.VerifyMapper;
import com.kadia.kblogserber.service.CommonService;
import com.kadia.kblogserber.service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyServiceImp implements VerifyService {
    @Autowired
    private VerifyMapper verifyMapper;

    @Autowired
    private InfoMapper infoMapper;

    @Autowired
    private CommonService commonService;

    @Override
    public void sendVerifyAndRecord(String email) throws ServicEexception {
        String verifyString = RandomString.getCharAndNum(6);
        Verify verify = new Verify();
        verify.setVerify(verifyString);
        verify.setTime(Timer.getTime());
        verify.setEmail(email);
        Info info = infoMapper.getById(1);
        String context= MailHtml.template("您的验证码为【"+verifyString+"】请在5分钟内完成验证，若非你本人所操作，可忽略此邮件",info.getEmail());
        try{
            verifyMapper.save(verify);
            commonService.sendMail(email,"验证码【"+verifyString+"】",context);
        }catch (ServicEexception e){
            throw new ServicEexception(e.getMessage());
        }catch (Exception e){
            throw new ServicEexception("出现了罕见的错误，请稍后再试");
        }
    }

    @Override
    public Verify verifyAbleAndDelete(String verifyString) throws ServicEexception {
        Verify verify = verifyMapper.findOneByVerifyAndTimeGreaterThan(verifyString,Timer.getTime()-300);
        if(verify != null){
            verifyMapper.delete(verify);
        }
        return verify;
    }
}
