package cn.com.taiji.controller;

import cn.com.taiji.domain.UserInfo;
import cn.com.taiji.service.UserService;
import cn.com.taiji.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by 李老八.
 * 2018/12/17.22.40
 **/
@RequestMapping("/User")
@Controller
public class UserController {
    @Autowired
    private UserService userService;

//    跳转到前台
    @RequestMapping(value = "/hello")
    public String tiao(Model model){
        model.addAttribute("userInfo",new UserInfo());
        return "main";
    }


    //    跳转到前台
    @RequestMapping(value = "/hello1")
    public String zhuan(Model model){
        return "userInfo-list";
    }



//前台个人中心
//   个人中心，通过用户Id查询用户

    @GetMapping(value = "/selectOne/{id}")
    @ResponseBody
    public Message FindByUserId(@PathVariable("id") Integer userId, HttpServletResponse response, Model model) {
        response.setCharacterEncoding("UTF-8");
        UserInfo user = userService.findByUserId(userId);
       if(null!= user){
           model.addAttribute("user",user);
           return Message.success("通过Id查询成功！");
       } else {
           return Message.fail("通过Id查询失败！");
       }

    }

    //    修改个人信息
    @RequestMapping(value = "/updateUserInfo")
    @ResponseBody
    public Message updateUserInfo(UserInfo userInfo, HttpServletResponse response) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePasswd = encoder.encode(userInfo.getUserPassword());
        userInfo.setUserPassword(encodePasswd);
        boolean result =userService.updataUserInfo(userInfo);
        if(result == true){
            return Message.success("个人信息修改成功！");
        } else {
            return Message.fail("个人信息修改失败！");
        }

    }

    //修改个人密码
    @RequestMapping("/changePwd")
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
            return Message.success("修改成功！");

        } else {
            return Message.fail("修改失败！");
        }

    }

    //后台个人中心
//    符合状态的所有用户
//    查询符合状态的用户信息
    @RequestMapping(value = "/findStatusUser")
    @ResponseBody
    public Message findStatusUser(Model model) {

        List<UserInfo> userInfos = userService.findByUserStatus("1");
        if (userInfos != null) {
            model.addAttribute("userInfos",userInfos);
             return Message.success("查询成功！").add("user",userInfos);
        } else {
            return Message.fail("查询失败！");
        }
    }

// 后台新增用户
    @RequestMapping(value = "/addUser")
    public Message addUser(UserInfo userInfo, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        userInfo.setUserStatus("1");
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePasswd = encoder.encode(userInfo.getUserPassword());
        userInfo.setUserPassword(encodePasswd);
        boolean result = userService.insert(userInfo);
        if(result == true){
            return Message.success("新增成功！");
        } else {
            return Message.fail("新增失败！");
        }
    }

    //    后台假删除
    @RequestMapping(value = "/deleteUserInfo/{id}")
    @ResponseBody
    public Message deleteUserInfo(@PathVariable("id") Integer userId, HttpServletResponse response) {

        boolean result =userService.deleteUserInfo(userId);
        if(result == true){
            return Message.success("假删除成功！");
        } else {
            return Message.fail("假删除失败！");
        }

    }

}

