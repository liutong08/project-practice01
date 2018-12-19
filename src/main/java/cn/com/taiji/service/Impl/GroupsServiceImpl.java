package cn.com.taiji.service.Impl;

import cn.com.taiji.domain.Groups;
import cn.com.taiji.repository.GroupsRepository;
import cn.com.taiji.repository.LabelsRepository;
import cn.com.taiji.service.GroupsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

/**
 * @ Author     ：liutong.
 * @ Date       ：Created in 16:37 2018/12/17
 * @ Description：${description}
 * @ Modified By：
 * @ Version:     $version
 */
@Service
public class GroupsServiceImpl implements GroupsService {
    private Logger logger = LoggerFactory.getLogger(GroupsServiceImpl.class);

    @Autowired
    private GroupsRepository groupsRepository;
//    @Autowired
//    private LabelsRepository labelsRepository;

    //查询所有讨论组
    @Transactional()
    @Override
    public List<Groups> findAllGroups() {
        List<Groups> groupsList = groupsRepository.findAll();
        logger.info("---GroupsServiceImpl---" + groupsList);
        return groupsList;
    }

    //通过讨论组ID查询讨论组的信息
    @Transactional()
    @Override
    public Groups findGroupById(Integer id) {
        Groups group = groupsRepository.findOne(id);
        //获取当前讨论组的标签信息
        group.getLabelsList();
        //获取当前讨论组的贴子信息
        group.getPostsList();
        //获取当前讨论组的组员信息
        group.getUserInfoList();
        //获取当前讨论组组长
        group.getUserInfo();
        return group;
    }
}
