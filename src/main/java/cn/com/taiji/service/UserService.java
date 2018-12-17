package cn.com.taiji.service;

/**
 * @author : wangsuide
 * @version 1.0
 * @Project: project-practice01
 * @Package cn.com.taiji.service
 * @Description: TODO
 * @date Date : 2018年12月16日 13:48
 */

public interface UserService {

    int saveroles(Integer userId, Integer roleId);

    int deleteroles(Integer id, Integer role);
}
