package cn.com.taiji.controller;

import cn.com.taiji.domain.Permission;
import cn.com.taiji.domain.Role;
import cn.com.taiji.service.PermissionService;
import cn.com.taiji.service.RoleService;
import cn.com.taiji.util.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    public static Integer num = null;

    //给角色增加权限
    @ResponseBody
    @PostMapping("/addpermission")
    public Message addrole(Integer roleId,Integer[] permissionIds){

        for(Integer permissionId : permissionIds){
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
    @PostMapping("/deletepermission")
    public Message deletepermission(Integer roleId,Integer[] permissionIds){

        for(Integer permissionId : permissionIds){
            num = permissionService.deletepermission(permissionId,roleId);
        }
        if (num != null ){
            return Message.success("删除成功！");
        }else {
            return Message.fail("删除失败！");
        }
    }

    //查找角色的权限
    @GetMapping("/getonerole/{roleId}")
    public String getOnePermission(@PathVariable Integer roleId,Model model){

        model.addAttribute("roleId",roleId);
        Role onerole = roleService.findByRoleId(roleId);
        //用户已有的权限列表
        List<Permission> havePermission = new ArrayList<>();
        //用户没有的权限列表
        List<Permission> unPermission = new ArrayList<>();
        List<Permission> permission= permissionService.findAll();

        //判断用户有无的权限
        for (Permission p : permission){
            List<Role> role = p.getRolesList();
                if (role.contains(onerole)){
                    havePermission.add(p);
                }else {
                    unPermission.add(p);
                }
        }
        //传输用户已有的权限列表
        model.addAttribute("havePermission",havePermission);
        //传输用户没有的权限列表
        model.addAttribute("unPermission",unPermission);
        return "permission-role";
    }

    //得到所有的权限
    @GetMapping("/getallpermission")
    public String getpermission(Model model){
        List<Permission> permission= permissionService.findAll();
        model.addAttribute("permission",permission);
        return "permission-list";
    }

    //新增加权限
    @PostMapping("/insertpermission")
    @ResponseBody
    public Message insertpermission(Permission permission){
       Permission permission1 = permissionService.save(permission);
       if (permission1 != null){
           return Message.success("新增成功");
       }else {
           return Message.fail("新增失败");
       }
    }

    //得到单个权限
    @GetMapping("/getonepermission/{permissionId}")
    @ResponseBody
    public Message getonepermission(@PathVariable Integer permissionId){
       Permission permission = permissionService.findByPermissionId(permissionId);
        return Message.success("成功").add("permission",permission);
    }

    //修改权限信息
    @PostMapping(value = "/updatepermissionbyid")
    @ResponseBody
    public Message updatepermissionbyid(Permission permission) {

        boolean result =permissionService.updatePermissionById(permission);
        if(result == true){
            return Message.success("权限信息修改成功！");
        } else {
            return Message.fail("权限信息修改失败！");
        }
    }

    //删除权限
    @PostMapping("/deletepermissionbyid/{permissionId}")
    @ResponseBody
    public Message deletepermissionbyid(@PathVariable Integer permissionId){
        Integer num = permissionService.deletepermissionById(permissionId);
        if (num == 0){
            return Message.fail("删除失败");
        }else {
            return Message.success("删除成功");
        }
    }
}
