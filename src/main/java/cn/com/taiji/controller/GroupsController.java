package cn.com.taiji.controller;

import cn.com.taiji.domain.Groups;
import cn.com.taiji.domain.Labels;
import cn.com.taiji.domain.Posts;
import cn.com.taiji.domain.UserInfo;
import cn.com.taiji.dto.GroupUserDto;
import cn.com.taiji.service.GroupsService;
import cn.com.taiji.service.LabelsService;
import cn.com.taiji.service.UserService;
import cn.com.taiji.util.Message;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.UUID;

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
    @Autowired
    private LabelsService labelsService;

    //查询所有的讨论组(分页)
    @GetMapping ("/findAllPages/{num}")
    public String findAllPages(@PathVariable Integer num, Model model){

        Page<Groups> groupsPage = groupsService.findGroupsNoCriteria(num, 5);
        //是否有下一页
        boolean hasNext = groupsPage.hasNext();
        model.addAttribute("hasNext",hasNext);
        logger.info ("hasNext"+hasNext);
        //判断是否有上一页
        boolean hasPrevious = groupsPage.hasPrevious();
        logger.info ("hasPrevious"+hasPrevious);
        model.addAttribute("hasPrevious",hasPrevious);
        //总共的页数
        int totalPages = groupsPage.getTotalPages();//返回分页总数。
        logger.info ("分页总数"+totalPages);
        model.addAttribute("totalPages",totalPages);
        //总条数
        long totalElements = groupsPage.getTotalElements();//返回元素总数。
        logger.info("总共多少条"+totalElements);
        model.addAttribute("totalElements",totalElements);

        List<Groups> groupsList = groupsPage.getContent();//将所有数据返回为List
        logger.info("当前页的所有数据"+groupsList);
        //当前的页数号
        int pageNum = groupsPage.getNumber();
        model.addAttribute("pageNum",pageNum);
        logger.info("pageNum"+pageNum);
        //每页的显示数量
        int pageSize = groupsPage.getNumberOfElements();
        logger.info("pageSize"+pageSize);

        model.addAttribute("pageSize",pageSize);
        model.addAttribute("groupsPage",groupsPage);

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
        return Message.success("成功").add("groupById", groupById).add("userInfoList", userInfoList);
    }


    //讨论组添加
    @PostMapping("/addGroup")
    @ResponseBody
    public Message addGroup(Groups groups,Integer labelId, MultipartFile file) throws IOException {
        logger.info(groups.toString());


        //判断前台传入的groups是否有ID，没有 就调service新增
        if (groups.getGroupId() != null) {
            return Message.fail("添加失败");
        } else {
            if (file != null) {
                //随机生成文件名
                String filename = UUID.randomUUID().toString().replace("-", "");
                logger.info("filename====" + filename);
                //获取文件的后缀
                String ext = FilenameUtils.getExtension(file.getOriginalFilename());
                logger.info("ext====" + ext);
                //写入文件到指定位置
                file.transferTo(new File(filename + "." + ext));
                //把文件名写入数据库
                groups.setGroupIco(filename + "." + ext);
                groups.setGroupCreateTime(new Date());
                groups.setGroupStatus("1");
                //建立讨论组和标签的关系
                List<Labels> labelsList=new ArrayList<>();
                Labels label = labelsService.findLabelById(labelId);
                labelsList.add(label);
                groups.setLabelsList(labelsList);
                //建立标签和讨论组的关系
                Groups groups1 = groupsService.addGroup(groups);
                List<Groups> groupsList=new ArrayList<>();
                groupsList.add(groups1);
                label.setGroupsList(groupsList);

                return Message.success("添加成功");
            } else {
                groups.setGroupCreateTime(new Date());
                groups.setGroupStatus("1");
                groupsService.addOrUpdateGroup(groups);
                return Message.success("添加成功");
            }

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
    public Message updateGroupInfo(Groups groups, MultipartFile file) throws IOException {

        logger.info("groupid==" + Integer.toString(groups.getGroupId()) + "----");
        //判断前台传入的groupId是否为空，不为空就调service修改
        if (groups.getGroupId() != null) {
            Groups byGroupId = groupsService.findGroupById(groups.getGroupId());
            //判断传入的文件是否存在，存在则带文件更新
            if (file != null) {
                //随机生成文件名
                String filename1 = UUID.randomUUID().toString().replace("-", "");
                logger.info("filename1====" + filename1);
                //获取文件的后缀
                String ext1 = FilenameUtils.getExtension(file.getOriginalFilename());
                logger.info("ext1====" + ext1);
                //写入文件到指定位置
                file.transferTo(new File(filename1 + "." + ext1));
                byGroupId.setGroupDescription(groups.getGroupDescription());
                byGroupId.setGroupName(groups.getGroupName());
                byGroupId.setGroupIco(filename1 + "." + ext1);
                groupsService.addOrUpdateGroup(byGroupId);
                return Message.success("修改成功");
            } else {
                //file不存在则不带文件更新
                byGroupId.setGroupDescription(groups.getGroupDescription());
                byGroupId.setGroupName(groups.getGroupName());
                groupsService.addOrUpdateGroup(byGroupId);
                return Message.success("修改成功");
            }


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
            //页面公用一个按钮对status进行修改，不同status值对应不同的状态
            if (byGroupId.getGroupStatus().equals("1")) {
                byGroupId.setGroupStatus("-1");
                groupsService.addOrUpdateGroup(byGroupId);
            } else {
                byGroupId.setGroupStatus("1");
                groupsService.addOrUpdateGroup(byGroupId);
            }

            return Message.success("操作成功");

        } else {
            return Message.fail("操作失败");
        }

    }

    //前台查询单个讨论组  郑伟
    @GetMapping("/getGroupsById/{groupsId}")
    public String getGroupsById(@PathVariable Integer groupsId,Model model){
        Groups group = groupsService.findGroupById(groupsId);
        //讨论组组长
        UserInfo groupLader = group.getUserInfo();
        if(groupLader!=null){
            model.addAttribute("groupLader",groupLader);
        }else {
            //如果没有组长就给个假数据
            UserInfo userInfo=new UserInfo();
            userInfo.setUserName("无组长");
            userInfo.setUserPic("001.png");
            model.addAttribute("groupLader",userInfo);
        }

        model.addAttribute("group",group);
        //查询讨论组中的成员
        List<UserInfo> userInfoList = group.getUserInfoList();
        model.addAttribute("userInfoList",userInfoList);
        //查询讨论组中的帖子
        List<Posts> postsList = group.getPostsList();
        model.addAttribute("postsList",postsList);
        return "single-group";
    }


}
