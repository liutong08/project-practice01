package cn.com.taiji.controller;

import cn.com.taiji.domain.Groups;
import cn.com.taiji.domain.Labels;
import cn.com.taiji.service.LabelsService;
import cn.com.taiji.util.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import java.util.List;

/**
 * @ Author     ：liutong.
 * @ Date       ：Created in 12:40 2018/12/18
 * @ Description：${description}
 * @ Modified By：
 * @ Version:     $version
 */
@Controller
public class LabelsController {
    private Logger logger = LoggerFactory.getLogger(GroupsController.class);

    @Autowired
    private LabelsService labelsService;

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
}
