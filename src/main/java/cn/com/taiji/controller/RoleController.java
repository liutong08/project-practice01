package cn.com.taiji.controller;

import cn.com.taiji.domain.Role;
import cn.com.taiji.service.RoleService;
import cn.com.taiji.service.UserService;
import cn.com.taiji.util.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private UserService userService;

    public static Integer num = null;

    //给用户增加角色
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

    //给用户删除角色
    @ResponseBody
    @PostMapping("/deleterole")
    public Message deleterole(Integer id,Integer[] roless){

        for(Integer role : roless){
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
    public Message insertrole(Role role, HttpServletResponse response){

        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=format.format(date);
        role.setRoleXx(time);
        Role role1 = roleService.insertrole(role);
        if ( role1 != null ){
            return Message.success("新增成功！");
        }else {
            return Message.fail("新增失败！");
        }
    }

    //删除角色
    @ResponseBody
    @PostMapping("/deleterolebyid/{roleId}")
    public Message deleterole(@PathVariable Integer roleId){

         num = roleService.deleterole(roleId);
        if ( num != null ){
            return Message.success("删除成功！");
        }else {
            return Message.fail("删除失败！还有员工有此角色...");
        }
    }

    //查找自己的角色
    @GetMapping("/getonerole/{id}")
    public String getOneRole(@PathVariable Integer id,Model model){

        model.addAttribute("userId",id);
        List<Role> allrole= roleService.findallrole();
        List<Role> haverole= userService.findOneRole(id);
        model.addAttribute("onerole",haverole);

        List<Role> unrole = new ArrayList<>();

        for(Role role : allrole){
                if (haverole.contains(role)){
                }else {
                    unrole.add(role);
                }
        }
        model.addAttribute("unroles",unrole);
        return "role-permission";
    }

    //查询所有角色
    @GetMapping("/getallrole")
    public String getallrole(Model model){
        List<Role> allrole= roleService.findallrole();
        model.addAttribute("allrole",allrole);
        return "role-list";
    }

    //修改角色信息
    @PostMapping(value = "/updaterole")
    @ResponseBody
    public Message updateUserInfo(Role role) {

        boolean result =roleService.updataRole(role);
        if(result == true){
            return Message.success("角色信息修改成功！");
        } else {
            return Message.fail("角色信息修改失败！");
        }
    }

    //按照ID查询role
    @ResponseBody
    @GetMapping("/getrolebyid/{roleId}")
    public Message getonerole(@PathVariable Integer roleId){
        Role role = roleService.findByRoleId(roleId);
        return Message.success("查询成功").add("role",role);
    }
}
