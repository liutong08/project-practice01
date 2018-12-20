package cn.com.taiji.service;

import cn.com.taiji.domain.Groups;

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
    public int updateGroupStatus(Integer id);
    public int realDeleteGroup(Integer id);
}
