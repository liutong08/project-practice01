package cn.com.taiji.controller;

import cn.com.taiji.domain.*;
import cn.com.taiji.dto.GroupUserDto;
import cn.com.taiji.service.GroupsService;
import cn.com.taiji.service.PostsService;
import cn.com.taiji.service.RepliesService;
import cn.com.taiji.util.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Author     ：liutong.
 * @ Date       ：Created in 16:40 2018/12/17
 * @ Description：${description}
 * @ Modified By：
 * @ Version:     $version
 */
@Controller
public class GroupsController {
    private Logger logger = LoggerFactory.getLogger(GroupsController.class);

    @Autowired
    private GroupsService groupsService;
    @Autowired
    private PostsService postsService;
    @Autowired
    private RepliesService repliesService;

    //假删除讨论组 前台传回groupId，返回message ajax判断
    @ResponseBody
    @PostMapping("/updateGroupStatus")
    public Message deleteGroup(Groups groups) {
        int i = groupsService.updateGroupStatus(groups.getGroupId());
        if (i > 0) {
            return Message.success("删除成功");
        } else {
            return Message.fail("删除失败");
        }
    }

//    //真删除讨论组，以及与标签中间表，与用户中间组员表，删贴子删回帖
//    @PostMapping("/realDeleteGroup")
//    @ResponseBody
//    public Message realDeleteGroup(Groups groups){
//        //查询指定讨论组信息
//        Groups group = groupsService.findGroupById(groups.getGroupId());
//        //查询制定讨论组的贴子列表
//        List<Posts> groupPostsList = group.getPostsList();
////        判断贴子是否为空
//        if(groupPostsList!=null){
//            //遍历贴子列表
//            for (Posts posts : groupPostsList) {
//                //查找指定贴子
//                Posts posts1 = postsService.findPostById(posts.getPostId());
//                //查找指定贴子的回帖
//                List<Replies> posts1RepliesList = posts1.getRepliesList();
//                //判断是否有回帖
//                if(posts1RepliesList!=null) {
//                    //删除回帖列表
//                    repliesService.deleteReplyList(posts1RepliesList);
//                }
//            }
//            postsService.deletePostsList(groupPostsList);
//        }
//
//
//        int i = groupsService.realDeleteGroup(groups.getGroupId());
//        if(i>0){
//            return Message.success("删除成功");
//        }else {
//            return Message.fail("删除失败");
//        }
//    }

    //查询所有的讨论组 带组长信息 返回 讨论组列表 页面 thymeleaf显示 使用DTO封装在一个类中
    @GetMapping("/findAllGroups")
    public String findAllGroups(Model model) {

        logger.info("findAllGroups---");

        //全部讨论组
        List<GroupUserDto> groupUserDtoList = new ArrayList<>();
        List<Groups> groupsList = groupsService.findAllGroups();
        for (Groups group : groupsList) {
            UserInfo groupUserInfo = group.getUserInfo();
            GroupUserDto groupUserDto = new GroupUserDto(group, groupUserInfo);
            groupUserDtoList.add(groupUserDto);
        }
        model.addAttribute("groupUserDtoList", groupUserDtoList);

        //返回至讨论组列表页面
        return "group-back-list";
    }

    //通过GroupId查询指定讨论，组长，组员，标签，贴子 返回 指定讨论组 页面 thymeleaf显示
    @GetMapping("/findOneGroup")
    public String findOneGroup(Groups groups, Model model) {

        logger.info("findOneGroup---");

        //通过ID查询指定讨论组
        Groups group = groupsService.findGroupById(groups.getGroupId());
        model.addAttribute("group", group);
        logger.info("group---" + group);

        //查询当前讨论组的组长
        UserInfo groupUserInfo = group.getUserInfo();
        model.addAttribute("groupUserInfo", groupUserInfo);
        logger.info("groupUserInfo---" + groupUserInfo);

        //查询当前讨论组的组员
        List<UserInfo> groupUserInfoList = group.getUserInfoList();
        model.addAttribute("groupUserInfoList", groupUserInfoList);
        logger.info("groupUserInfoList---" + groupUserInfoList);

        //查询当前讨论组的全部标签
        List<Labels> groupLabelsList = group.getLabelsList();
        model.addAttribute("groupLabelsList", groupLabelsList);
        logger.info("groupLabelsList---" + groupLabelsList);

        //查询当前讨论组的全部贴子
        List<Posts> groupPostsList = group.getPostsList();
        model.addAttribute("groupPostsList", groupPostsList);
        logger.info("groupPostsList---" + groupPostsList);

        //返回至指定讨论组页面
        return "group-back-show-single";
    }


}
