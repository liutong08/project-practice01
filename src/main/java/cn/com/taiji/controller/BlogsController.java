package cn.com.taiji.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import cn.com.taiji.domain.*;
import cn.com.taiji.dto.BlogUserDto;
import cn.com.taiji.dto.GroupUserDto;
import cn.com.taiji.dto.UserCommentsDto;
import cn.com.taiji.service.BlogsService;
import cn.com.taiji.service.CommentsService;
import cn.com.taiji.service.LabelsService;
import cn.com.taiji.service.UserService;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import cn.com.taiji.util.Message;
import org.springframework.web.multipart.MultipartFile;

/**
 * @user
 */

@Controller
@RequestMapping("/Blogs")
public class BlogsController {

    private Logger logger = LoggerFactory.getLogger(BlogsController.class);

    @Autowired
    private BlogsService blogsService;
    @Autowired
    private UserService userService;
    @Autowired
    private LabelsService labelsService;


    @Autowired
    private CommentsService commentsService;


    @GetMapping("/getBlogById/{blogId}")
    //@ResponseBody
    public String getBlogById(@PathVariable Integer blogId, Model model) {
        //单条博客的内容
        Blogs blog = blogsService.getBlogByBlogId(blogId);
        model.addAttribute("blog", blog);
        //根据博客查询用户信息
        UserInfo blogUserInfo = blog.getUserInfo();
        model.addAttribute("blogUserInfo", blogUserInfo);
        //根据博客查出标签
        List<Labels> blogLabels = blog.getLabels();
        model.addAttribute("blogLabels", blogLabels);
        //查询当前博客用户的其他博客
        List<Blogs> blogsList = blogUserInfo.getBlogsList();
        model.addAttribute("blogsList", blogsList);
        //查询当前博客的所有评论
        List<Comments> blogCommentsList = blog.getCommentsList();
        //查询发表评论的用户
        List<UserCommentsDto> userCommentsDtoList = new ArrayList<>();
        for (Comments comments : blogCommentsList) {
            UserInfo commentsUserInfo = comments.getUserInfo();
            UserCommentsDto userCommentsDto = new UserCommentsDto(commentsUserInfo, comments);
            userCommentsDtoList.add(userCommentsDto);
            model.addAttribute("userCommentsDtoList", userCommentsDtoList);
        }

        return "single-blog";
    }


    //	根据ID查询博客
    @GetMapping("getBlogByBlogId")
    @ResponseBody
    public BlogUserDto getBlogByBlogId(Integer blogId) {

        Blogs blog = blogsService.getBlogByBlogId(blogId);
        UserInfo userInfo = blog.getUserInfo();
        BlogUserDto blogUserDto = new BlogUserDto(blog, userInfo);
        return blogUserDto;
    }

    //查询所有的博客(分页)(后台)
    @GetMapping("/findAllBlogs/{num}")
    public String findAllBlogs(@PathVariable Integer num, Model model) {

        Page<Blogs> blogsPage = blogsService.findBlogsNoCriteria(num, 5);
        //是否有下一页
        boolean hasNext = blogsPage.hasNext();
        model.addAttribute("hasNext", hasNext);
        logger.info("hasNext" + hasNext);
        //判断是否有上一页
        boolean hasPrevious = blogsPage.hasPrevious();
        logger.info("hasPrevious" + hasPrevious);
        model.addAttribute("hasPrevious", hasPrevious);
        //总共的页数
        int totalPages = blogsPage.getTotalPages();//返回分页总数。
        logger.info("分页总数" + totalPages);
        model.addAttribute("totalPages", totalPages);
        //总条数
        long totalElements = blogsPage.getTotalElements();//返回元素总数。
        logger.info("总共多少条" + totalElements);
        model.addAttribute("totalElements", totalElements);

        List<Blogs> blogsList = blogsPage.getContent();//将所有数据返回为List
        logger.info("当前页的所有数据" + blogsList);
        //当前的页数号
        int pageNum = blogsPage.getNumber();
        model.addAttribute("pageNum", pageNum);
        logger.info("pageNum" + pageNum);
        //每页的显示数量
        int pageSize = blogsPage.getNumberOfElements();
        logger.info("pageSize" + pageSize);

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("groupsPage", blogsPage);

        List<BlogUserDto> blogUserDtoList = new ArrayList<>();
        for (Blogs blog : blogsList) {
            UserInfo blogUserInfo = blog.getUserInfo();
            if (blogUserInfo != null) {
                BlogUserDto blogUserDto = new BlogUserDto(blog, blogUserInfo);
                blogUserDtoList.add(blogUserDto);
            } else {
                UserInfo userInfo = new UserInfo();
//				userInfo.setUserName("无组长");
                BlogUserDto blogUserDto = new BlogUserDto(blog, userInfo);
                blogUserDtoList.add(blogUserDto);
            }
        }
        model.addAttribute("blogUserDtoList", blogUserDtoList);
        return "blog-back-list";

    }


    //前台查询所有博客
    @GetMapping("/findAnyBlogs/{num}")
    public String findAnyBlogs(@PathVariable Integer num, Model model) {

        Page<Blogs> blogsPage = blogsService.findBlogsNoCriteria(num, 5);
        //是否有下一页
        boolean hasNext = blogsPage.hasNext();
        model.addAttribute("hasNext", hasNext);
        logger.info("hasNext" + hasNext);
        //判断是否有上一页
        boolean hasPrevious = blogsPage.hasPrevious();
        logger.info("hasPrevious" + hasPrevious);
        model.addAttribute("hasPrevious", hasPrevious);
        //总共的页数
        int totalPages = blogsPage.getTotalPages();//返回分页总数。
        logger.info("分页总数" + totalPages);
        model.addAttribute("totalPages", totalPages);
        //总条数
        long totalElements = blogsPage.getTotalElements();//返回元素总数。
        logger.info("总共多少条" + totalElements);
        model.addAttribute("totalElements", totalElements);

        List<Blogs> blogsList = blogsPage.getContent();//将所有数据返回为List
        logger.info("当前页的所有数据" + blogsList);
        //当前的页数号
        int pageNum = blogsPage.getNumber();
        model.addAttribute("pageNum", pageNum);
        logger.info("pageNum" + pageNum);
        //每页的显示数量
        int pageSize = blogsPage.getNumberOfElements();
        logger.info("pageSize" + pageSize);

        model.addAttribute("pageSize", pageSize);
        model.addAttribute("groupsPage", blogsPage);

        List<BlogUserDto> blogUserDtoList = new ArrayList<>();
        for (Blogs blog : blogsList) {
            UserInfo blogUserInfo = blog.getUserInfo();
            if (blogUserInfo != null) {
                BlogUserDto blogUserDto = new BlogUserDto(blog, blogUserInfo);
                blogUserDtoList.add(blogUserDto);
            } else {
                UserInfo userInfo = new UserInfo();
                BlogUserDto blogUserDto = new BlogUserDto(blog, userInfo);
                blogUserDtoList.add(blogUserDto);
            }
        }
        model.addAttribute("blogs", blogUserDtoList);
        //查询所有标签
        List<Labels> labelsList = labelsService.findAllLabels();
        model.addAttribute("labelsList", labelsList);
        return "blog";
    }

    @RequestMapping("/getBlogByBlogId/{blogId}")
    @ResponseBody
    public Message getBlogByBlogId(Model model, @PathVariable("blogId") Integer blogId) {
        Blogs blogs = blogsService.getBlogByBlogId(blogId);
        if (null != blogs) {
            model.addAttribute("Blogs", blogs);
            return Message.success("查询成功");
        } else {
            return Message.fail("查询失败");
        }
    }

//    @PostMapping("/saveBlog")
//    @ResponseBody
//    public Message saveBlog(Blogs blog) {
//        blog.setBlogCreateTime(new Date());
//        boolean result = blogsService.saveBlog(blog);
//        if (result == true) {
//            return Message.success("保存成功");
//        } else {
//            return Message.success("保存失败");
//        }
//    }

    @RequestMapping("/updateBlog")
    @ResponseBody
    public Message updateBlog(Blogs blog) {
        blog.setBlogCreateTime(new Date());
        boolean result = blogsService.updateBlog(blog);
        if (result == true) {
            return Message.success("保存成功");
        } else {
            return Message.success("保存失败");
        }
    }


    //    真删除
    @RequestMapping("/deleteBlog/{blogId}")
    @ResponseBody
    public Message deleteBlog(@PathVariable("blogId") Integer blogId) {
        boolean result = blogsService.deleteByBlogId(blogId);
        if (result == true) {
            return Message.success("删除成功");
        } else {
            return Message.success("删除失败");
        }
    }


    //发表博客 zhengwei
    @PostMapping("/addBlog")
    @ResponseBody
    public Message addBlog(Integer userId, Blogs blogs, MultipartFile file, Integer labelId) throws IOException {
        //判断用户是否登录
        if (userId != null) {
            UserInfo userInfo = userService.findByUserId(userId);
            List<Labels> labelsList = new ArrayList<>();
            List<Blogs> blogsList=new ArrayList<>();
            //判断是否是新的博客
            if (blogs.getBlogId() != null) {
                return Message.fail("该博客已存在");
            } else {
                if (file != null ) {
                    //带文件发表
                    //随机生成文件名
                    String filename1 = UUID.randomUUID().toString().replace("-", "");
                    logger.info("filename1====" + filename1);
                    //获取文件的后缀
                    String ext = FilenameUtils.getExtension(file.getOriginalFilename());
                    logger.info("ext====" + ext);
                    //写入文件到指定位置

                    file.transferTo(new File(filename1 + "." + ext));
                    blogs.setBlogCreateTime(new Date());
                    blogs.setBlogPic(filename1 + "." + ext);
                    blogs.setBlogStatus("1");
                    blogs.setUserInfo(userInfo);
                    //简历博客与标签的关系
                    //添加标签
                    Labels label = labelsService.findLabelById(labelId);
                    labelsList.add(label);
                    blogs.setLabels(labelsList);
                    Blogs blogs1 = blogsService.saveBlog(blogs);
                    blogsList.add(blogs1);
                    label.setBlogsList(blogsList);

                    return Message.success("发表成功");
                } else {
                    //不带文件发表
                    blogs.setBlogCreateTime(new Date());
                    blogs.setBlogStatus("1");
                    blogs.setUserInfo(userInfo);
                    Labels label = labelsService.findLabelById(labelId);
                    labelsList.add(label);
                    blogs.setLabels(labelsList);
                    Blogs blogs1 = blogsService.saveBlog(blogs);
                    blogsList.add(blogs1);
                    label.setBlogsList(blogsList);
                    return Message.success("发表成功");
                }
            }
        } else {
            return Message.fail("请登录后再发表");
        }
    }
}
