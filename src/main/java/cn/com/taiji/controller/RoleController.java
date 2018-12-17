package cn.com.taiji.controller;

import cn.com.taiji.service.PermissionService;
import cn.com.taiji.service.RoleService;
import cn.com.taiji.service.UserService;
import cn.com.taiji.util.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author : wangsuide
 * @version 1.0
 * @Project: project-practice01
 * @Package cn.com.taiji.controller
 * @Description: TODO
 * @date Date : 2018年12月17日 10:35
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserService userService;

    public static Integer num = null;

    //给角色新增加权限
    @ResponseBody
    @PostMapping("/addrole")
    public Message addrole(Integer id,Integer[] roles){

        for(Integer role : roles){
            num = userService.saveroles(id,role);
        }
        if (num != null ){
            return Message.success("授权成功！");
        }else {
            return Message.fail("授权失败！");
        }
    }

    //给角色删除权限
    @ResponseBody
    @DeleteMapping("/deleterole")
    public Message deleterole(Integer id,Integer[] roles){

        for(Integer role : roles){
             num = userService.deleteroles(id,role);
        }
        if (num != null ){
            return Message.success("删除成功！");
        }else {
            return Message.fail("删除失败！");
        }
    }

    //新增加角色
    @ResponseBody
    @PostMapping("/insertrole")
    public Message insertrole(Role role){

        Role role1 = roleService.insertrole(role);
        if ( role1 != null ){
            return Message.success("新增成功！");
        }else {
            return Message.fail("新增失败！");
        }
    }

    //删除角色
    @ResponseBody
    @PostMapping("/deleterole")
    public Message deleterole(Integer roleId){

         num = roleService.deleterole(roleId);
        if ( num != null ){
            return Message.success("新增成功！");
        }else {
            return Message.fail("新增失败！");
        }
    }
}
