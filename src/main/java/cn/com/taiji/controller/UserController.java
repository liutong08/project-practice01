package cn.com.taiji.controller;

import cn.com.taiji.domain.Blogs;
import cn.com.taiji.domain.Groups;
import cn.com.taiji.domain.Posts;
import cn.com.taiji.domain.UserInfo;
import cn.com.taiji.service.UserService;
import cn.com.taiji.util.Message;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author : wangsuide
 * @version 1.0
 * @Project: project-practice01
 * @Package cn.com.taiji.controller
 * @Description: TODO
 * @date Date : 2018年12月17日 10:35
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //个人中心，通过用户Id查询用户
    @GetMapping(value = "/selectOne/{userId}")
    @ResponseBody
    public Message FindByUserId(@PathVariable("userId") Integer userId,Model model) {
        UserInfo user = userService.findByUserId(userId);
        if(null!= user){
            model.addAttribute("user",user);
            return Message.success("通过Id查询成功！").add("user",user);
        } else {
            return Message.fail("通过Id查询失败！");
        }

    }

    //修改个人信息
    @PostMapping("/updateUserInfo")
    @ResponseBody
    public Message updateUserInfo(UserInfo userInfo,MultipartFile file) throws IOException {
        if ( file != null ){
            //保存图片到
            String name = UUID.randomUUID().toString().replaceAll("-", "");
            String ext = FilenameUtils.getExtension(file.getOriginalFilename());
            file.transferTo( new File(name + "." + ext));
            userInfo.setUserPic(name + "." + ext);
        }

        boolean result =userService.updataUserInfo(userInfo);
        if(result == true){
            return Message.success("您的信息修改成功！");
        } else {
            return Message.fail("信息修改失败，请重新修改！");
        }
    }

    //修改个人密码
    @PostMapping("/changePwd")
    @ResponseBody
    public Message changePwd(String userPassword, String newUserPwd, Integer userId) {
        // 判断密码是否做了变更
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "";
        UserInfo user = userService.findByUserId(userId);
        password = user.getUserPassword();
        if (encoder.matches(userPassword,password)) {
            user.setUserPassword(encoder.encode(newUserPwd));
            userService.updataUserInfo(user);
            return Message.success("修改成功！请重新登录");
        } else {
            return Message.fail("旧密码不正确！");
        }
    }

    //符合状态的所有用户
    @GetMapping(value = "/findStatusUser")
    public String findStatusUser(Model model,HttpServletResponse response) {
        List<UserInfo> userinfos = userService.findByUserStatus();
        model.addAttribute("userinfos",userinfos);
        return "userinfo-list";
    }

    //新增用户
    @PostMapping(value = "/adduser")
    @ResponseBody
    public Message addUser(UserInfo userInfo, MultipartFile file) throws IOException {

            if (file != null){
                //保存图片到
                String name = UUID.randomUUID().toString().replaceAll("-", "");
                //jpg uploadFile
                String ext = FilenameUtils.getExtension(file.getOriginalFilename());
                file.transferTo(new File(name + "." + ext));
                userInfo.setUserPic(name + "." + ext);
            }
            //设置用户状态
            userInfo.setUserStatus("1");
            //设置用户创建时间
            Date date=new Date();
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time=format.format(date);
            userInfo.setUserXx(time);
            //设置用户密码
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String encodePasswd = encoder.encode(userInfo.getUserPassword());
            userInfo.setUserPassword(encodePasswd);

            Integer num = 1;
            boolean results = userService.insert(userInfo,num);
            if (results == true) {
                //给用户设置初始角色，2号为普通用户，不是后台管理员
//                userService.saveroles(userInfo.getUserId(),2);
                return Message.success("新增成功！");
            } else {
                return Message.fail("新增失败！");
            }
        }

    //后台假删除
    @PostMapping(value = "/deleteUserInfo/{userId}")
    @ResponseBody
    public Message deleteUserInfo(@PathVariable("userId") Integer userId, HttpServletResponse response) {
        boolean result =userService.deleteUserInfo(userId);
        if(result == true){
            return Message.success("删除成功！");
        } else {
            return Message.fail("删除失败！");
        }
    }

    //前台个人中心 根据个人Id查询个人所有信息
    //根据个人Id查询相关博客信息以及讨论组信息
    @GetMapping("/findOneUser/{userId}")
    public  String findOneUser(@PathVariable Integer userId ,Model model){
        if (userId == null){
            return "login";
        }else {
            //根据个人Id查询个人所有信息
            UserInfo user = userService.findByUserId(userId);
            model.addAttribute("user", user);

            //根据个人Id查询相关博客信息
            List<Blogs> userBlogsList = user.getBlogsList();
            model.addAttribute("userBlogsList",userBlogsList);

            //根据个人Id查询讨论组信息
            List<Groups> userGroupsList = user.getGroupsList();
            model.addAttribute("userGroupsList",userGroupsList);

            //根据个人Id查询帖子信息(前端个人中心没写因为样式不会弄)
            List<Posts> userPostsList = user.getPostsList();
            model.addAttribute("userPostsList",userPostsList);
            return "person";
        }
    }
}
