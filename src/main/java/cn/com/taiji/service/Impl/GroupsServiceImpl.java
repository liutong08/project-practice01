package cn.com.taiji.service.Impl;

import cn.com.taiji.repository.GroupsRepository;
import cn.com.taiji.service.GroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ Author     ：liutong.
 * @ Date       ：Created in 16:37 2018/12/17
 * @ Description：${description}
 * @ Modified By：
 * @ Version:     $version
 */
@Service
public class GroupsServiceImpl implements GroupsService {
    @Autowired
    private GroupsRepository groupsRepository;
}
