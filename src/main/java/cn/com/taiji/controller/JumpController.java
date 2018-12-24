package cn.com.taiji.controller;

import cn.com.taiji.domain.UserInfo;
import cn.com.taiji.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author : wangsuide
 * @version 1.0
 * @Project: project-practice01
 * @Package cn.com.taiji.controller
 * @Description: TODO
 * @date Date : 2018年12月16日 19:23
 */
@Controller
public class JumpController {

    @Autowired
    private UserService userService;

    @RequestMapping({"/login"})
    public String login(){
        return "login";
    }

    @RequestMapping("/login-eamil")
    public String loginEamil(){
        return "login-eamil";
    }

    @RequestMapping("/login-phone")
    public String loginPhone(){
        return "login-phone";
    }

    //跳转到后台主页，需要有权限
    @RequestMapping("/main")
    public String main(){
        return "main";
    }

    @RequestMapping("/toindex")
    public String toindex(HttpSession session){
        //得到用户信息
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        String Loginname = userDetails.getUsername();
        UserInfo userInfo = userService.findByUserLoginName(Loginname);
        session.setAttribute("userInfo",userInfo);
        return "index";
    }

    @RequestMapping("/login-error")
    public String unpermission(Model model){
        model.addAttribute("errormsg","您的账号或密码可能输入错误...");
        return "login";
    }

    @GetMapping("/role")
    public String role(){
        return "role-permission";
    }

    @GetMapping("/permission")
    public String permission(){
        return "permission-role";
    }

    @GetMapping("/userinfo_list")
    public String userinfolist(){
        return "userinfo-list";
    }


    @RequestMapping({"/index","/"})
    public String index(){
        return "redirect:/index/toIndex";
    }

    @RequestMapping("/single")
    public String single(){
        return "single-blog";
    }

    @RequestMapping("/blogList")
    public String blogList(){
        return "redirect:/Blogs/findAnyBlogs/"+0;
    }

    @RequestMapping("/addBlog")
    public String addBlog(){
        return "blog-add";
    }
}
