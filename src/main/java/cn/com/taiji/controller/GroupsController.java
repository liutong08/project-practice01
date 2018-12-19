package cn.com.taiji.controller;

import cn.com.taiji.domain.Groups;
import cn.com.taiji.domain.Labels;
import cn.com.taiji.domain.Posts;
import cn.com.taiji.domain.UserInfo;
import cn.com.taiji.dto.GroupUserDto;
import cn.com.taiji.service.GroupsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebParam;
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

    //查询所有的讨论组
    @GetMapping("/findAllGroups")
    public String findAllGroups(Model model){

        logger.info("findAllGroups---");

        //全部讨论组
        List<Groups> groupsList=groupsService.findAllGroups();
//        model.addAttribute("groupsList",groupsList);
//        logger.info("groupsList---"+groupsList);

        List<GroupUserDto> groupUserDtoList=new ArrayList<>();
        for(Groups group:groupsList){
            UserInfo groupUserInfo = group.getUserInfo();
            GroupUserDto groupUserDto=new GroupUserDto(group,groupUserInfo);
            groupUserDtoList.add(groupUserDto);
        }
        model.addAttribute("groupUserDtoList",groupUserDtoList);
        return "group_back_list";
    }

    //查询指定讨论组
    @GetMapping("/findOneGroup")
    @ResponseBody
    public Model findOneGroup(Integer id,Model model){

        logger.info("findOneGroup---");

        //通过ID查询指定讨论组
        Groups group = groupsService.findGroupById(id);
        model.addAttribute("group",group);
        logger.info("group---"+group);

        //查询当前讨论组的组长
        UserInfo groupUserInfo = group.getUserInfo();
        model.addAttribute("groupUserInfo",groupUserInfo);
        logger.info("groupUserInfo---"+groupUserInfo);

        //查询当前讨论组的组员
        List<UserInfo> groupUserInfoList = group.getUserInfoList();
        model.addAttribute("groupUserInfoList",groupUserInfoList);
        logger.info("groupUserInfoList---"+groupUserInfoList);

        //查询当前讨论组的全部标签
        List<Labels> groupLabelsList = group.getLabelsList();
        model.addAttribute("groupLabelsList",groupLabelsList);
        logger.info("groupLabelsList---"+groupLabelsList);

        //查询当前讨论组的全部贴子
        List<Posts> groupPostsList = group.getPostsList();
        model.addAttribute("groupPostsList",groupPostsList);
        logger.info("groupPostsList---"+groupPostsList);

        return model;
    }


}
