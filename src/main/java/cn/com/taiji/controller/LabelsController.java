package cn.com.taiji.controller;

import cn.com.taiji.domain.Blogs;
import cn.com.taiji.domain.Groups;
import cn.com.taiji.domain.Labels;
import cn.com.taiji.domain.UserInfo;
import cn.com.taiji.dto.BlogUserDto;
import cn.com.taiji.service.BlogsService;
import cn.com.taiji.service.LabelsService;
import cn.com.taiji.util.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Author     ：liutong.
 * @ Date       ：Created in 12:40 2018/12/18
 * @ Description：${description}
 * @ Modified By：
 * @ Version:     $version
 */
@Controller
@RequestMapping("/labels")
public class LabelsController {
    private Logger logger = LoggerFactory.getLogger(LabelsController.class);

    @Autowired
    private LabelsService labelsService;
    @Autowired
    private BlogsService blogsService;

    //新增标签 前台传回label对象 传出Message ajax判断
    @PostMapping("/addLabel")
    @ResponseBody
    public Message addLabel(Labels label) {
        Labels save = labelsService.addLabel(label);
        if (save != null) {
            return Message.success("新增成功");
        } else {
            return Message.fail("新增失败");
        }
    }

    //删除标签 前台传回labelId 传出message ajax判断
    @PostMapping("/deleteLabel")
    @ResponseBody
    public Message deleteLabel(Labels label) {
        int i = labelsService.deleteLabelByLabelId(label.getLabelId());
        if (i > 0) {
            return Message.success("删除成功");
        } else {
            return Message.fail("删除失败");
        }
    }

    //查询所有标签信息 返回 标签 页面 thymeleaf显示
    @GetMapping("/findAllLabels")
    public String findAllLabels(Model model) {

        logger.info("findAllLabels---");

        //查询所有标签
        List<Labels> labelsList = labelsService.findAllLabels();
        model.addAttribute("labelsList", labelsList);
        logger.info("labelsList---" + labelsList);

        return "label-back-list";
    }

    //通过标签的ID查询标签
    @GetMapping("/findById")
    @ResponseBody
    public Message findById(Integer id) {
        Labels label = labelsService.findLabelById(id);
        return Message.success("成功").add("label",label);
    }

    //修改讨论组信息
    @PostMapping("/updateLabelInfo")
    @ResponseBody
    public Message updateLabelInfo(Labels labels) {

        logger.info("labelId==" + Integer.toString(labels.getLabelId()) + "----");
        //判断前台传入的groupId是否为空，不为空就调service修改
        if (labels.getLabelId() != null) {
            Labels label = labelsService.findLabelById(labels.getLabelId());
            label.setLabelName(labels.getLabelName());
            label.setLabelDescription(labels.getLabelDescription());
            labelsService.addLabel(label);
            return Message.success("修改成功");
        } else {
            return Message.fail("修改失败");
        }
    }

    //通过LabelId查询标签信息，讨论组信息 前台传回labelId 返回 指定标签 页面 使用thymeleaf显示
    @GetMapping("/findOneLabels")
    public String findOneLabels(Labels labels, Model model) {

        logger.info("findOneLabels");

        //查询指定标签
        Labels label = labelsService.findLabelById(labels.getLabelId());
        model.addAttribute("label", label);
        logger.info("label---" + label);

        //查询拥有该标签的讨论组
        List<Groups> labelGroupsList = label.getGroupsList();
        model.addAttribute("labelGroupsList", labelGroupsList);
        logger.info("labelGroupsList---" + labelGroupsList);

        return "label-back-show-single";
    }

//    查找标签对应的博客
    @GetMapping("/getonelabel/{labelId}")
    public String getOneBlog(@PathVariable Integer labelId,Model model){
//根据标签查博客
        Page<Blogs> blogsPage = blogsService.findBlogsCriteria(labelId, 0);
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
        // model.addAttribute("blogs",list);
        //查询所有标签
        return "blog";
    }



}
