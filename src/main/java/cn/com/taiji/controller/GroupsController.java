package cn.com.taiji.controller;

import cn.com.taiji.domain.Groups;
import cn.com.taiji.domain.Labels;
import cn.com.taiji.domain.Posts;
import cn.com.taiji.domain.UserInfo;
import cn.com.taiji.dto.GroupUserDto;
import cn.com.taiji.service.GroupsService;
import cn.com.taiji.service.UserService;
import cn.com.taiji.util.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * @ Author     ：liutong.
 * @ Date       ：Created in 16:40 2018/12/17
 * @ Description：${description}
 * @ Modified By：
 * @ Version:     $version
 */
@Controller
@RequestMapping("/groups")
public class GroupsController {
    private Logger logger = LoggerFactory.getLogger(GroupsController.class);

    @Autowired
    private GroupsService groupsService;
    @Autowired
    private UserService userService;

    //查询所有的讨论组
    @GetMapping("/findAllGroups")
    public String findAllGroups(Model model) {

        logger.info("findAllGroups---");

        //全部讨论组
        List<Groups> groupsList = groupsService.findAllGroups();
//        model.addAttribute("groupsList",groupsList);
//        logger.info("groupsList---"+groupsList);

        List<GroupUserDto> groupUserDtoList = new ArrayList<>();
        for (Groups group : groupsList) {
            UserInfo groupUserInfo = group.getUserInfo();
            if (groupUserInfo != null) {
                GroupUserDto groupUserDto = new GroupUserDto(group, groupUserInfo);
                groupUserDtoList.add(groupUserDto);
            } else {
                UserInfo userInfo = new UserInfo();
                userInfo.setUserName("无组长");
                GroupUserDto groupUserDto = new GroupUserDto(group, userInfo);
                groupUserDtoList.add(groupUserDto);
            }
        }
        model.addAttribute("groupUserDtoList", groupUserDtoList);
        return "group-back-list";
    }

    //查询指定讨论组
    @GetMapping("/findOneGroup")
    //@ResponseBody
    public Model findOneGroup(Integer id, Model model) {

        logger.info("findOneGroup---");

        //通过ID查询指定讨论组
        Groups group = groupsService.findGroupById(id);
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

        return model;
    }

    //通过讨论组的ID查询讨论组
    @GetMapping("/findById")
    @ResponseBody
    public Message findById(Integer id) {
        Groups groupById = groupsService.findGroupById(id);
        List<UserInfo> userInfoList = groupById.getUserInfoList();
        return Message.success("成功").add("groupById",groupById).add("userInfoList",userInfoList);
    }




    //讨论组添加
    @PostMapping("/addGroup")
    @ResponseBody
    public Message addGroup(Groups groups) {
        logger.info(groups.toString());
        //判断前台传入的groups是否有ID，没有 就调service新增
        if (groups.getGroupId() != null) {
            return Message.fail("添加失败");
        } else {
            groups.setGroupCreateTime(new Date());
            groups.setGroupStatus("1");
            groupsService.addOrUpdateGroup(groups);
            return Message.success("添加成功");
        }

    }

    //添加讨论组组长
    @PostMapping("/updateGroup")
    @ResponseBody
    public Message updateGroup(Integer groupId, Integer userId) {

        logger.info("groupid==" + Integer.toString(groupId) + "----" + "userid==" + Integer.toString(userId));
        //判断前台传入的groupId是否为空，不为空就调service添加
        if (groupId != null) {
            Groups byGroupId = groupsService.findGroupById(groupId);
            UserInfo user = userService.findByUserId(userId);
            byGroupId.setUserInfo(user);
            groupsService.addOrUpdateGroup(byGroupId);
            return Message.success("更新成功");
        } else {
            return Message.fail("更新失败");
        }

    }

    //修改讨论组信息
    @PostMapping("/updateGroupInfo")
    @ResponseBody
    public Message updateGroupInfo(Groups groups) {

        logger.info("groupid==" + Integer.toString(groups.getGroupId()) + "----");
        //判断前台传入的groupId是否为空，不为空就调service修改
        if (groups.getGroupId() != null) {
            Groups byGroupId = groupsService.findGroupById(groups.getGroupId());
            byGroupId.setGroupDescription(groups.getGroupDescription());
            byGroupId.setGroupName(groups.getGroupName());
            groupsService.addOrUpdateGroup(byGroupId);
            return Message.success("修改成功");

        } else {
            return Message.fail("修改失败");
        }

    }

    //删除讨论组，假删除，实际上是把status字段设置为-1
    @PostMapping("/updateGroupByStatus")
    @ResponseBody
    public Message updateGroupByStatus(Integer groupId) {

        logger.info("groupid==" + Integer.toString(groupId) + "----");
        //判断前台传入的groupId是否为空，不为空就调service删除
        if (groupId != null) {
            Groups byGroupId = groupsService.findGroupById(groupId);
            byGroupId.setGroupStatus("-1");
            groupsService.addOrUpdateGroup(byGroupId);
            return Message.success("删除成功");

        } else {
            return Message.fail("删除失败");
        }

    }


}
