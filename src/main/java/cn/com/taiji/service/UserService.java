package cn.com.taiji.service;

import cn.com.taiji.domain.Role;
import cn.com.taiji.domain.UserInfo;
import cn.com.taiji.domain.UserVO;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author : wangsuide
 * @version 1.0
 * @Project: project-practice01
 * @Package cn.com.taiji.service
 * @Description: TODO
 * @date Date : 2018年12月18日 14:33
 */
public interface UserService {

    int saveroles(Integer userId, Integer roleId);

    int deleteroles(Integer id, Integer role);

    List<Role> findOneRole(Integer userId);

    //前台个人中心 通过用户Id查询用户
    UserInfo findByUserId(Integer userId);

    //更新功能
    boolean updataUserInfo(UserInfo userInfo);

    //后台删除功能
     boolean deleteUserInfo(Integer userId);

    //查询符合条件的所有员工
    boolean insert(UserInfo usrInfo,Integer num);

    //后台个人中心 查询状态为1的所有合格用户
    List<UserInfo> findByUserStatus();

    UserInfo findByUserLoginName(String username);

    UserInfo findByUserPhone(String username);

    UserInfo findByUserEmail(String username);

    Page<UserInfo> finduserByname(Integer page, Integer size, UserVO userVO);

}

