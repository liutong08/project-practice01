package cn.com.taiji.controller;

import cn.com.taiji.domain.Groups;
import cn.com.taiji.domain.Posts;
import cn.com.taiji.domain.Replies;
import cn.com.taiji.domain.UserInfo;
import cn.com.taiji.dto.PostUserGroupDto;
import cn.com.taiji.service.GroupsService;
import cn.com.taiji.service.PostsService;
import cn.com.taiji.service.RepliesService;
import cn.com.taiji.service.UserService;
import cn.com.taiji.util.Message;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ Author     ：liutong.
 * @ Date       ：Created in 13:02 2018/12/18
 * @ Description：${description}
 * @ Modified By：
 * @ Version:     $version
 */

@Controller
@RequestMapping("/posts")
public class PostsController {
    private Logger logger = LoggerFactory.getLogger(PostsController.class);

    @Autowired
    private PostsService postsService;
    @Autowired
    private GroupsService groupsService;
    @Autowired
    private UserService userService;
    @Autowired
    private RepliesService repliesService;

    //删除贴子，并删除贴子内的回帖 传回postId 传出Message ajax判断
    @PostMapping("/deletePost")
    @ResponseBody
    public Message deletePost(Posts posts) {
        //获取贴子信息
        Posts post = postsService.findPostById(posts.getPostId());
        //获取当前贴子的回帖信息
        List<Replies> postRepliesList = post.getRepliesList();
        //判断若有回帖则删除
        if (postRepliesList != null) {
            //遍历删除多条回帖
            repliesService.deleteReplyList(postRepliesList);
        }
        //删除贴子
        int i = postsService.deletePostByPostId(posts.getPostId());
        //判断删除是否成功
        if (i > 0) {
            return Message.success("删除成功");
        } else {
            return Message.fail("删除失败");
        }
    }

    @GetMapping("/gotoAddPost")
    public String gotoAddPost(@RequestParam Integer groupId,Model model){
        model.addAttribute("groupId",groupId);
        return "post-add";
    }

    //添加贴子(发帖) 前台返回post信息，讨论组id，作者id 传出Message ajax判断
    @PostMapping("/addPost")
    @ResponseBody
    public Message addPost(Posts post, Integer groupId, Integer userId, MultipartFile file) throws IOException {
        //获取讨论组信息
        Groups postGroup = groupsService.findGroupById(groupId);
        //获取作者信息
        UserInfo postUserInfo = userService.findByUserId(userId);
        if (post.getPostId() != null) {
            return Message.fail("该帖子已存在");
        } else {
            if (file != null) {
                //随机生成文件名
                String filename2 = UUID.randomUUID().toString().replace("-", "");
                logger.info("filename1====" + filename2);
                //获取文件的后缀
                String ext = FilenameUtils.getExtension(file.getOriginalFilename());
                logger.info("ext====" + ext);
                //写入文件到指定位置

                file.transferTo(new File(filename2 + "." + ext));
                post.setPostPic(filename2 + "." + ext);
                post.setPostCreateTime(new Date());
                post.setGroups(postGroup);
                post.setUserInfo(postUserInfo);
                postsService.addPost(post);
                return Message.success("发表成功");
            } else {
                post.setPostCreateTime(new Date());
                post.setGroups(postGroup);
                post.setUserInfo(postUserInfo);
                postsService.addPost(post);
                return Message.success("发表成功");
            }
        }

    }

    //查找所有贴子，以及帖子作者和讨论组 返回 贴子列表页面  thymeleaf显示
    @GetMapping("/findAllPosts")
    public String findAllPosts(Model model) {
        List<PostUserGroupDto> postUserGroupDtos = new ArrayList<>();
        List<Posts> allPosts = postsService.findAllPosts();
        for (Posts post : allPosts) {
            Groups postGroups = post.getGroups();
            UserInfo postUserInfo = post.getUserInfo();
            postUserGroupDtos.add(new PostUserGroupDto(post, postUserInfo, postGroups));
        }
        model.addAttribute("postUserGroupDtos", postUserGroupDtos);

        return "post-back-list";
    }

    //后台查询指定帖子 传回postId 传出指定贴子信息 返回到模态框中
    @GetMapping("/showPost")
    @ResponseBody
    public Message showPost(Integer postId) {
        Posts post = postsService.findPostById(postId);
        UserInfo userInfo = post.getUserInfo();
        return Message.success("成功").add("post", post).add("userInfo", userInfo);
    }

    //通过贴子postId查询贴子内容，回帖以及回帖作者  前台返回id 返回 指定贴子 页面 thymeleaf显示
    @GetMapping("/findOnePost")
    public String findOnePost(Posts posts, Model model) {

        logger.info("findOnePost---");

        //查询指定贴子
        Posts post = postsService.findPostById(posts.getPostId());
        model.addAttribute("post", post);
        logger.info("post---" + post);

        //查询贴子的所有回帖
        List<Replies> postRepliesList = post.getRepliesList();
        model.addAttribute("postRepliesList", postRepliesList);
        logger.info("postRepliesList---" + postRepliesList);

        //查询回帖的作者信息
        List<UserInfo> userInfoList = new ArrayList<>();
        for (Replies replies : postRepliesList) {
            UserInfo repliesUserInfo = replies.getUserInfo();
            userInfoList.add(repliesUserInfo);
        }
        model.addAttribute("userInfoList", userInfoList);
        logger.info("userInfoList---" + userInfoList);

        //查询当前贴子的所在讨论组
        Groups postGroups = post.getGroups();
        model.addAttribute("postGroups", postGroups);
        logger.info("postGroups---" + postGroups);

        //查询当前帖子的作者信息
        UserInfo postUserInfo = post.getUserInfo();
        model.addAttribute("postUserInfo", postUserInfo);
        logger.info("postUserInfo---" + postUserInfo);

        return "";
    }

}
