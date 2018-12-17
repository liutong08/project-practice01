package cn.com.taiji.service.Impl;

import cn.com.taiji.repository.UserRepository;
import cn.com.taiji.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : wangsuide
 * @version 1.0
 * @Project: project-practice01
 * @Package cn.com.taiji.service.Impl
 * @Description: TODO
 * @date Date : 2018年12月16日 13:48
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public int saveroles(Integer userId, Integer roleId) {
        return userRepository.saveroles(userId,roleId);
    }

    @Override
    public int deleteroles(Integer id, Integer role) {
        return userRepository.deleteroles(id,role);
    }
}
