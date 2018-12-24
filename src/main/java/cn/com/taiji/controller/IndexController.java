package cn.com.taiji.controller;

import cn.com.taiji.domain.Blogs;
import cn.com.taiji.domain.Groups;
import cn.com.taiji.dto.GroupUserDto;
import cn.com.taiji.dto.GroupsBlogsDto;
import cn.com.taiji.service.BlogsService;
import cn.com.taiji.service.GroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName IndexController
 * @Description
 * @Author --zhengwei
 * @Date 2018/12/22 10:42
 * @Version
 */

@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private BlogsService blogsService;
    @Autowired
    private GroupsService groupsService;

    @GetMapping("toIndex")
   // @ResponseBody
    public String  toIndex(Model model) {
        List<Blogs> blogsList = blogsService.getAllBlog();
        List<Groups> groupsList = groupsService.findAllGroups();
        List<GroupsBlogsDto> list1=new ArrayList<>();
       ;

        //首页显示六条的博客和讨论组
        for (int i = 0; i <6; i++) {
            Blogs blogs = blogsList.get(i);
            Groups groups = groupsList.get(i);
            GroupsBlogsDto groupsBlogsDto=new GroupsBlogsDto(blogs,groups);
            list1.add(groupsBlogsDto);
        }
        model.addAttribute("groupsBlogDto", list1);

        return "index";
    }

}
