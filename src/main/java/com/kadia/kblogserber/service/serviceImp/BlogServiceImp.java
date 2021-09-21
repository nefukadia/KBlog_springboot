package com.kadia.kblogserber.service.serviceImp;

import com.kadia.kblogserber.common.MailHtml;
import com.kadia.kblogserber.common.ServicEexception;
import com.kadia.kblogserber.common.State;
import com.kadia.kblogserber.common.Timer;
import com.kadia.kblogserber.entity.Blog;
import com.kadia.kblogserber.entity.Subscribe;
import com.kadia.kblogserber.mapper.BlogMapper;
import com.kadia.kblogserber.mapper.InfoMapper;
import com.kadia.kblogserber.mapper.SubscribeMapper;
import com.kadia.kblogserber.service.BlogService;
import com.kadia.kblogserber.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class BlogServiceImp implements BlogService {
    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private SubscribeMapper subscribeMapper;

    @Autowired
    private InfoMapper infoMapper;

    @Autowired
    private CommonService commonService;

    @Override
    public List<Blog> getBlog(String title, Integer page, Boolean isUser) throws ServicEexception {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("time").descending());
        if(!isUser){
            return blogMapper.findAllByTitleContainingAndState(title, State.blogOk, pageable).getContent();
        }else{
            return blogMapper.findAllByTitleContaining(title, pageable).getContent();
        }
    }

    @Override
    public Blog getBlogInfo(Integer id, Boolean edit) throws ServicEexception {
        if(edit){
            return blogMapper.getById(id);
        }else{
            return blogMapper.findOneByIdAndState(id, State.blogOk);
        }
    }

    @Override
    public void blogViewIncrease(Integer id) throws ServicEexception {
        Blog tmp = blogMapper.findOneByIdAndState(id, State.blogOk);
        if(tmp != null){
            tmp.setView(tmp.getView()+1);
            blogMapper.save(tmp);
        }
    }

    @Override
    public long countOpenBlog() throws ServicEexception {
        return blogMapper.countByState(State.blogOk);
    }

    @Override
    public long sumView() throws ServicEexception {
        try{
            return blogMapper.sumViewByState(1);
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public void doBlog(Integer id, String title, String content) throws ServicEexception {
        Blog blog;
        if(id != null)
            blog = blogMapper.getById(id);
        else
            blog = new Blog();
        blog.setId(id);
        blog.setTitle(title);
        blog.setContent(content);
        blog.setTime(Timer.getTime());
        if(id == null){
            blog.setState(1);
            blog.setView(0);
        }
        blogMapper.save(blog);
        if(id == null){
            List<Subscribe> list = subscribeMapper.findAll();
            for (Subscribe subscribe : list) {
                commonService.sendMail(
                        subscribe.getEmail(),
                        "你订阅的博客有新的推送啦",
                        MailHtml.template("你订阅的博客发布了新文章【"+title+"】,快来查收吧！！",infoMapper.getById(1).getEmail()));
            }
        }
    }

    @Override
    public void changeBlog(Integer id, Integer type) throws ServicEexception {
        Blog blog = blogMapper.getById(id);
        if(type == 0){
            blog.setState(blog.getState()==1?0:1);
            blogMapper.save(blog);
        }else{
            blogMapper.delete(blog);
        }
    }
}
