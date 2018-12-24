package cn.com.taiji.controller;

import cn.com.taiji.domain.*;
import cn.com.taiji.dto.GroupUserDto;
import cn.com.taiji.dto.PostUserGroupDto;
import cn.com.taiji.dto.ReplyUserDto;
import cn.com.taiji.service.*;
import cn.com.taiji.util.Message;
import com.sun.xml.internal.bind.marshaller.MinimumEscapeHandler;
import javafx.geometry.Pos;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.awt.SystemColor.info;

/**
 * @ Author     ：liutong.
 * @ Date       ：Created in 22:23 2018/12/20
 * @ Description：${description}
 * @ Modified By：
 * @ Version:     $version
 */
@Controller
@RequestMapping("/frontGroup")
public class FrontGroupController {
    private Logger logger = LoggerFactory.getLogger(FrontGroupController.class);

    @Autowired
    private GroupsService groupsService;
    @Autowired
    private LabelsService labelsService;
    @Autowired
    private PostsService postsService;
    @Autowired
    private RepliesService repliesService;
    @Autowired
    private UserService userService;

    @GetMapping("/groupSingle")
    public String groupSingle(@RequestParam Integer groupId, Model model) {
        return "group-front-single";
    }

    //添加回帖
    @Transactional
    @PostMapping("/addReply")
    @ResponseBody
    public Message addReply(UserInfo userInfo,Posts posts,Replies replies){

        //查询当前登录的用户信息
        UserInfo user = userService.findByUserId(userInfo.getUserId());

        //将用户信息加入到回帖信息中
        replies.setUserInfo(user);

        //创建回帖信息时间为当前时间
        replies.setReplyCreateTime(new Date());

        //查找帖子信息
        Posts post1 = postsService.findPostById(posts.getPostId());

        //将帖子信息加入到回帖信息中
        replies.setPosts(post1);

        //添加回帖
        Replies reply = repliesService.addReply(replies);

        //获取帖子信息
        Posts post = postsService.findPostById(posts.getPostId());

        //将回帖信息加入当前帖子中
        List<Replies> repliesList = post.getRepliesList();
        boolean add = repliesList.add(replies);

        //将帖子信息更新
        Posts posts1 = postsService.addPost(post);
        if (posts1!=null){
            return Message.success("发表成功").add("post",post);
        }else {
            return Message.fail("发表失败");
        }
    }

    //用户加入讨论组
    @Transactional
    @PostMapping("/addIntoGroup")
    @ResponseBody
    public Message addIntoGroup(UserInfo userInfo,Groups groups){

        //获取讨论组信息
        Groups group = groupsService.findGroupById(groups.getGroupId());

        //获取讨论组中的组员信息
        List<UserInfo> userInfoList = group.getUserInfoList();
        logger.info(userInfoList.toString());

        //获取当前登录的用户信息
        UserInfo user = userService.findByUserId(userInfo.getUserId());

        //将当前登录用户加入该讨论组
        userInfoList.add(user);
        logger.info(userInfoList.toString());
        logger.info(group.toString());

        //更新讨论组
        boolean b = groupsService.addOrUpdateGroup(group);
        if(b){
            return Message.success("欢迎加入");
        }else{
            return Message.fail("抱歉，加入失败");
        }
    }

    //判断用户是否在当前讨论组
    @Transactional
    @GetMapping("/checkUser")
    @ResponseBody
    public Message checkUser(UserInfo userInfo, Groups group) {

        if(userInfo.getUserId()!=null) {

            //获取当前讨论组
            Groups group1 = groupsService.findGroupById(group.getGroupId());
            logger.info(group1.toString());

            //获取当前讨论组的组员信息
            List<UserInfo> userInfoList = group1.getUserInfoList();
            logger.info(userInfoList.toString());
            Boolean b = false;
            for (UserInfo info : userInfoList) {

                //判断当前用户是否在讨论组
                if (info.getUserId().equals(userInfo.getUserId())) {
                    b = true;
                }
            }
            if (b) {
                return Message.success("可以发帖");
            } else {
                return Message.fail("您不在当前讨论组，是否考虑加入该讨论组");
            }
        }else{
            return Message.empty("无法获取您的信息，请先登录");
        }
    }

    //跳转讨论组首页
    @GetMapping("/groupsIndex")
    public String groupFrontIndex(Model model) {
        //查询讨论组信息
        List<Groups> groupsList = groupsService.findAllByGroupStatus();
        //查询讨论组组长信息
        List<GroupUserDto> groupUserDtoList = new ArrayList<>();
        for (Groups groups : groupsList) {
            UserInfo userInfo = groups.getUserInfo();
            if (userInfo != null) {
                groupUserDtoList.add(new GroupUserDto(groups, userInfo));
            } else {
                UserInfo userInfo1 = new UserInfo();
                userInfo1.setUserName(null);
                groupUserDtoList.add(new GroupUserDto(groups, userInfo1));
            }
        }
        model.addAttribute("groupUserDtoList", groupUserDtoList);
        model.addAttribute("label", new Labels());

        //查询标签信息
        List<Labels> labelsList = labelsService.findAllLabels();
        model.addAttribute("labelsList", labelsList);

        //查询所有贴子
        List<Posts> postsList = postsService.findAllPosts();
        List<PostUserGroupDto> postUserGroupDtoList=new ArrayList<>();
        for (Posts posts : postsList) {
            UserInfo info = posts.getUserInfo();
            Groups groups = posts.getGroups();
            postUserGroupDtoList.add(new PostUserGroupDto(posts,info,groups));
        }
        model.addAttribute("postUserGroupDtoList", postUserGroupDtoList);

        //查询所有回帖
        List<ReplyUserDto> replyUserDtoList = new ArrayList<>();
        //查询回帖作者
        List<Replies> repliesList = repliesService.findAllReplies();
        for (Replies replies : repliesList) {
            UserInfo userInfo = replies.getUserInfo();
            replyUserDtoList.add(new ReplyUserDto(replies, userInfo));
        }
        model.addAttribute("replyUserDtoList", replyUserDtoList);

        return "group-front-index";
    }


    //获取指定标签讨论组
    @GetMapping("/groupsIndexByLabel")
    public String groupsIndexByLabel(@RequestParam Integer labelId, Model model) {

        //查询指定标签
        Labels label = labelsService.findLabelById(labelId);
        model.addAttribute("label", label);
        //查询讨论组信息
        List<Groups> groupsList = label.getGroupsList();

        //查询讨论组组长信息
        List<GroupUserDto> groupUserDtoList = new ArrayList<>();
        if(groupsList!=null) {
            for (Groups groups : groupsList) {
                if(groups.getGroupStatus().equals("1")) {
                    UserInfo userInfo = groups.getUserInfo();
                    if (userInfo != null) {
                        groupUserDtoList.add(new GroupUserDto(groups, userInfo));
                    } else {
                        UserInfo userInfo1 = new UserInfo();
                        userInfo1.setUserName(null);
                        groupUserDtoList.add(new GroupUserDto(groups, userInfo1));
                    }
                }
            }
        }
        model.addAttribute("groupUserDtoList", groupUserDtoList);

        //查询标签信息
        List<Labels> labelsList = labelsService.findAllLabels();
        model.addAttribute("labelsList", labelsList);

        //查询指定标签下讨论组的贴子
        List<PostUserGroupDto> postUserGroupDtoList=new ArrayList<>();
        for (Groups groups : groupsList) {
            List<Posts> postsList = groups.getPostsList();
            for (Posts posts : postsList) {
                UserInfo info = posts.getUserInfo();
                postUserGroupDtoList.add(new PostUserGroupDto(posts,info,groups));
            }
        }
        model.addAttribute("postUserGroupDtoList", postUserGroupDtoList);

        //查询所有回帖
        List<ReplyUserDto> replyUserDtoList = new ArrayList<>();
        //查询回帖作者
        List<Replies> repliesList = repliesService.findAllReplies();
        for (Replies replies : repliesList) {
            UserInfo userInfo = replies.getUserInfo();
            replyUserDtoList.add(new ReplyUserDto(replies, userInfo));
        }
        model.addAttribute("replyUserDtoList", replyUserDtoList);

        return "group-front-index";
    }

    //跳转指定讨论帖
    @GetMapping("/postSingle")
    public String postSingle(@RequestParam Integer postId, Model model) {
        //查询贴子信息
        Posts post = postsService.findPostById(postId);
        //查询贴子所在讨论组信息
        Groups groups = post.getGroups();
        //查询贴子作者信息
        UserInfo userInfo = post.getUserInfo();
        PostUserGroupDto postUserGroupDto = new PostUserGroupDto(post, userInfo, groups);
        model.addAttribute("postUserGroupDto", postUserGroupDto);

        List<Posts> postsList = postsService.findPostsByUserId(userInfo.getUserId());
        model.addAttribute("postsList", postsList);

        List<ReplyUserDto> replyUserDtoList = new ArrayList<>();

        //查询回帖信息
        List<Replies> repliesList = post.getRepliesList();

        //查询回帖用户信息
        for (Replies replies : repliesList) {
            UserInfo userInfo1 = replies.getUserInfo();
            replyUserDtoList.add(new ReplyUserDto(replies, userInfo1));
        }
        model.addAttribute("replyUserDtoList", replyUserDtoList);

        return "post-front-single";
    }
}
