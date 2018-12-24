package cn.com.taiji.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.com.taiji.domain.*;
import cn.com.taiji.dto.BlogUserDto;
import cn.com.taiji.dto.GroupUserDto;
import cn.com.taiji.service.BlogsService;
import cn.com.taiji.service.CommentsService;
import cn.com.taiji.service.LabelsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import cn.com.taiji.util.Message;

@Controller
@RequestMapping("/Blogs")
public class BlogsController {

    private Logger logger = LoggerFactory.getLogger(GroupsController.class);

    @Autowired
    private BlogsService blogsService;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private LabelsService labelsService;

//    @RequestMapping("/getAllBlog")
//    public String getAllBlog(Model model) {
// 		List<Blogs> listBlogs = blogsService.getAllBlog();
//
//			model.addAttribute("listBlogs", listBlogs);
//
//		return "blog-back-list";
//	}
//	@RequestMapping(value = "/findStatusBlogs")
//
//	public String findStatusBlogs(Model model) {
//
//		List<Blogs> listBlogs = blogsService.findByBlogStatus("1");
//
//			model.addAttribute("listBlogs",listBlogs);
//
//			return "blog-back-list";
//		}

    //	根据ID查询博客
    @GetMapping("getBlogByBlogId")
    @ResponseBody
    public BlogUserDto getBlogByBlogId(Integer blogId) {

        Blogs blog = blogsService.getBlogByBlogId(blogId);
        UserInfo userInfo = blog.getUserInfo();
        BlogUserDto blogUserDto = new BlogUserDto(blog, userInfo);
        return blogUserDto;
    }

    //查询所有的博客(分页)
    @GetMapping("/findAllBlogs/{num}")
    public String findAllBlogs(@PathVariable Integer num, Model model) {

        Page<Blogs> blogsPage = blogsService.findBlogsNoCriteria(num, 10);
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


    //后台个人中心
//    符合状态的所有用户
//    查询符合状态的用户信息
//    @RequestMapping(value = "/findStatusBlog")
//    public String findStatusBlog(Model model) {
//
//        List<Blogs> blogs = blogsService.findByBlogStatus("1");
//        model.addAttribute("blogs", blogs);
//        //查询所有标签
//        List<Labels> labelsList = labelsService.findAllLabels();
//        model.addAttribute("labelsList", labelsList);
//        logger.info("labelsList---" + labelsList);
//
//        return "blog";
//    }

    //前台查询所有博客
    @GetMapping("/findAnyBlogs/{num}")
    public String findAnyBlogs(@PathVariable Integer num, Model model) {

        Page<Blogs> blogsPage = blogsService.findBlogsNoCriteria(num, 6);
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






    //
//    /**
//     * 未完成
//     * @param model
//     * @param userId
//     * @return
//     */
//    @RequestMapping("/getAllBlogByUserId")
//    public Message getAllBlogByUserId(Model model, Integer userId) {
//    	List<Blogs> listBlogs = blogsService.getAllBlogByUserId(userId);
//    	if(null != listBlogs) {
//    		model.addAttribute("listBlogs", listBlogs);
//        	return Message.success("查询成功");
//    	}else {
//    		return Message.fail("查询失败");
//    	}
//    }
//
//    /**
//     *
//     * 未完成
//     * @param model
//     * @param labels
//     * @return
//     */
//    @RequestMapping("/getAllBlogByLabel")
//    public Message getAllBlogByLabel(Model model, List<Labels> labels) {
//    	List<Blogs> listBlogs = blogsService.getAllBlogByLabel(labels);
//    	if(null != listBlogs) {
//    		model.addAttribute("listBlogs", listBlogs);
//        	return Message.success("查询成功");
//    	}else {
//    		return Message.fail("查询失败");
//    	}
//    }
//
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

    @PostMapping("/saveBlog")
    @ResponseBody
    public Message saveBlog(Blogs blog) {
        blog.setBlogCreateTime(new Date());
        boolean result = blogsService.saveBlog(blog);
        if (result == true) {
            return Message.success("保存成功");
        } else {
            return Message.success("保存失败");
        }
    }

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


//    @RequestMapping("/deleteBlog/{blogId}")
//    @ResponseBody
//    public Message deleteBlog(@PathVariable("blogId") Integer blogId) {
//
//        if (blogId != null){
//        Blogs blog = blogsService.getBlogByBlogId(blogId);
//        if (blog.getBlogStatus().equals("1")){
//            blog.setBlogStatus("0");
//            blogsService.saveBlog(blog);
//        }else {
//            blog.setBlogStatus("1");
//            blogsService.saveBlog(blog);
//        }
//        return Message.success("操作成功");
//        }else{
//        return Message.fail("操作失败");
//        }
//    }

//    真删除
    @RequestMapping("/deleteBlog/{blogId}")
    @ResponseBody
    public Message deleteBlog(@PathVariable("blogId")Integer blogId) {
        boolean result = blogsService.deleteByBlogId(blogId);
        if(result == true) {
            return Message.success("删除成功");
        }else {
            return Message.success("删除失败");
        }
    }


}
