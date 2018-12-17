package cn.com.taiji.controller;

import cn.com.taiji.service.PermissionService;
import cn.com.taiji.service.RoleService;
import cn.com.taiji.service.UserService;
import cn.com.taiji.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : wangsuide
 * @version 1.0
 * @Project: project-practice01
 * @Package cn.com.taiji.controller
 * @Description: TODO
 * @date Date : 2018年12月17日 13:17
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserService userService;

    public static Integer num = null;

    //给角色增加权限
    @ResponseBody
    @PostMapping("/addpermission")
    public Message addrole(Integer permissionId,Integer[] roleIds){

        for(Integer roleId : roleIds){
            num = permissionService.savepermission(permissionId,roleId);
        }
        if (num != null ){
            return Message.success("授权成功！");
        }else {
            return Message.fail("授权失败！");
        }
    }

    //给角色删除权限
    @ResponseBody
    @DeleteMapping("/deletepermission")
    public Message deletepermission(Integer permissionId,Integer[] roleIds){

        for(Integer roleId : roleIds){
            num = permissionService.deletepermission(permissionId,roleId);
        }
        if (num != null ){
            return Message.success("删除成功！");
        }else {
            return Message.fail("删除失败！");
        }
    }
}
