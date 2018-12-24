package cn.com.taiji.service;

import cn.com.taiji.domain.Groups;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @ Author     ：liutong.
 * @ Date       ：Created in 16:36 2018/12/17
 * @ Description：${description}
 * @ Modified By：
 * @ Version:     $version
 */
public interface GroupsService {

    public List<Groups> findAllGroups();
    public Groups findGroupById(Integer id);

    //新增讨论组
    boolean addOrUpdateGroup(Groups groups);
    Page<Groups> findGroupsNoCriteria(Integer page, Integer size);

    List<Groups> findAllByGroupStatus();

}
