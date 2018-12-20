package cn.com.taiji.controller;

import cn.com.taiji.domain.Posts;
import cn.com.taiji.domain.Replies;
import cn.com.taiji.domain.UserInfo;
import cn.com.taiji.service.PostsService;
import cn.com.taiji.service.RepliesService;
import cn.com.taiji.service.UserService;
import cn.com.taiji.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @ Author     ：liutong.
 * @ Date       ：Created in 23:33 2018/12/18
 * @ Description：${description}
 * @ Modified By：
 * @ Version:     $version
 */
@Controller
public class RepliesController {

    @Autowired
    private RepliesService repliesService;
    @Autowired
    private PostsService postsService;
    @Autowired
    private UserService userService;

    //删除回帖 传回repluyId 传出message ajax判断
    @PostMapping("/deleteReply")
    @ResponseBody
//    public int deleteReply(Replies reply){
    public Message deletePost(Replies reply) {
        int i = repliesService.deleteReplyByReplyId(reply.getReplyId());
        if (i > 0) {
            return Message.success("删除成功");
        } else {
            return Message.fail("删除失败");
        }
//        return i;
    }


    //添加回帖 前台传回reply信息，userId，postId 返回 Message ajax判断
    @PostMapping("/addReply")
    @ResponseBody
    public Message addReply(Replies reply, UserInfo userInfo, Posts post) {
//    public String addReply(Replies reply, UserInfo userInfo, Posts post){

        Posts posts = postsService.findPostById(post.getPostId());
        reply.setPosts(posts);
        UserInfo user = userService.findByUserId(userInfo.getUserId());
        reply.setUserInfo(user);
        reply.setReplyCreateTime(new Date());
        Replies replies = repliesService.addReply(reply);
        if (replies != null) {
            return Message.success("新增成功");
        } else {
            return Message.fail("新增失败");
        }
//        return replies.toString();
    }

}
