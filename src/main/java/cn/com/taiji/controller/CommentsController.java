package cn.com.taiji.controller;

import cn.com.taiji.domain.Blogs;
import cn.com.taiji.domain.Comments;
import cn.com.taiji.domain.UserInfo;
import cn.com.taiji.service.BlogsService;
import cn.com.taiji.service.CommentsService;

import cn.com.taiji.service.UserService;
import cn.com.taiji.util.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;


/**
 * @ClassName CommentsController
 * @Description
 * @Author --zhengwei
 * @Date 2018/12/22 18:26
 * @Version
 */
@Controller
public class CommentsController {
    @Autowired
    private CommentsService commentsSevice;
    @Autowired
    private UserService userService;
    @Autowired
    private BlogsService blogsService;
    Logger logger= LoggerFactory.getLogger(CommentsController.class);

    //添加指定博客的评论
    @PostMapping("/addComments")
    @ResponseBody
    public Message addComments(Integer blogId, Integer userId,String commentsContent) {
        if(null!=userId){
            Blogs blog = blogsService.getBlogByBlogId(blogId);
            logger.info("blog=="+blog);
            //通过用户登录的ID查询到用户的信息
            UserInfo userInfo = userService.findByUserId(userId);
            logger.info("userInfo=="+userInfo);

            Comments comments=new Comments();
            //通过前台传回的blogId查询到对应的博客

            //建立评论与用户的关系
            comments.setUserInfo(userInfo);
            //建立评论和博客的关系
            comments.setBlogs(blog);
            comments.setCommentCreateTime(new Date());
            comments.setCommentContent(commentsContent);
            logger.info("comments=="+comments);
            //插入评论信息
            commentsSevice.save(comments);
            return Message.success("评论成功");
        }
        else {
            return Message.fail("请登录后再做评论");
        }
    }
}
