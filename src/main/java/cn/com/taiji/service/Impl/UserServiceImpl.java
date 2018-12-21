package cn.com.taiji.service.Impl;

import cn.com.taiji.domain.UserInfo;
import cn.com.taiji.repository.UserRepository;
import cn.com.taiji.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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



//前台个人中心根据id查询
    @Override
    public UserInfo findByUserId(Integer userId) {
        UserInfo user = userRepository.findByUserId(userId);
        if (null != user){
            return user;
        }else {
            return null;
        }
    }
//更新用户信息
    @Transactional
    @Override
    public boolean updataUserInfo(UserInfo userInfo) {
        UserInfo old= userRepository.findByUserId(userInfo.getUserId());
//        userInfo.preUpdate();
        //本段代码可以代替get，set，不用一个一个赋值
        BeanUtils.copyProperties(userInfo, old);
        UserInfo user =  userRepository.saveAndFlush(old);
        if (user != null){
            return true;
        }else{
            return false;
        }
    }
//后台假删除员工
    @Override
    public boolean deleteUserInfo(Integer userId) {
        UserInfo userInfo = userRepository.findByUserId(userId);
        if (userInfo != null) {
            userInfo.setUserStatus("0");
            userRepository.save(userInfo);
            return true;
        } else {
            return false;
        }
    }

    ////    后台个人中心
//    实现查询符合"1"状态的所有员工
    @Override
    public List<UserInfo> findByUserStatus(String num) {
        List<UserInfo> listUserInfo=  userRepository.findByUserStatus(num);
        if (listUserInfo != null && listUserInfo.size() > 0){
            return listUserInfo;
        }else{
            return listUserInfo;
        }
    }
//新增员工
    @Override
    public boolean insert(UserInfo userInfo) {
         UserInfo user =  userRepository.save(userInfo);
         if (user != null){
             return true;
         }else{
             return false;
         }
    }



}
