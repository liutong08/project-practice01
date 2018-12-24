package cn.com.taiji.service.Impl;

import cn.com.taiji.domain.Role;
import cn.com.taiji.domain.UserInfo;
import cn.com.taiji.domain.UserVO;
import cn.com.taiji.repository.UserRepository;
import cn.com.taiji.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
@CacheConfig(cacheNames = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    //给user增加role
    @Transactional
    @Override
    public int saveroles(Integer userId, Integer roleId) {
        return userRepository.saveroles(userId,roleId);
    }

    //给user删除role
    @Transactional
    @Override
    public int deleteroles(Integer id, Integer role) {
        return userRepository.deleteroles(id,role);
    }

    //根据用户id查role
    @Override
    public List<Role> findOneRole(Integer userId) {
     UserInfo list = userRepository.findOne(userId);
        List<Role> rolelist = list.getRolesList();
        return rolelist;
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
    @CacheEvict(value="getalluser",allEntries=true)
    public boolean updataUserInfo(UserInfo userInfo) {

        UserInfo olduser= userRepository.findByUserId(userInfo.getUserId());
        if (userInfo.getUserPic() != null){
            olduser.setUserPic(userInfo.getUserPic());
        }
        //把修改的个人信息set到userInfo
        olduser.setUserLoginName(userInfo.getUserLoginName());
        olduser.setUserName(userInfo.getUserName());
        olduser.setUserEmail(userInfo.getUserEmail());
        olduser.setUserPhone(userInfo.getUserPhone());
        olduser.setUserAge(userInfo.getUserAge());
        olduser.setUserGender(userInfo.getUserGender());
        olduser.setUserAddress(userInfo.getUserAddress());
        UserInfo user =  userRepository.saveAndFlush(olduser);

        if (user != null){
            return true;
        }else{
            return false;
        }
    }

    //后台假删除员工
    @Transactional
    @Override
    @CacheEvict(value="getalluser",allEntries=true)
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

    //实现查询符合"1"状态的所有员工
    @Override
    @PostConstruct
    @Cacheable(value = "getalluser",keyGenerator = "wiselyKeyGenerator")
    public List<UserInfo> findByUserStatus() {
        String num ="1";
        List<UserInfo> listUserInfo=  userRepository.findByUserStatus(num);
        System.err.println("hahahahaha");
        if (listUserInfo != null && listUserInfo.size() > 0){
            return listUserInfo;
        }else{
            return listUserInfo;
        }
    }

    //新增员工
    @Transactional
    @Override
    @CacheEvict(value="getalluser",allEntries=true)
    public boolean insert(UserInfo userInfo,Integer num) {
         UserInfo user =  userRepository.save(userInfo);
         if (user != null){
             return true;
         }else{
             return false;
         }
    }

    //按照LoginName查找
    @Transactional
    @Override
    public UserInfo findByUserLoginName(String username) {
        return userRepository.findByUserLoginName(username);
    }

    //按照Phone查找
    @Override
    public UserInfo findByUserPhone(String username) {
        return userRepository.findByUserPhone(username);
    }

    //按照Email查找
    @Override
    public UserInfo findByUserEmail(String username) {
        return userRepository.findByUserEmail(username);
    }

    //分页查询
    @Override
    public Page<UserInfo> finduserByname(Integer page, Integer size, final UserVO userVO) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "userName");
        Page<UserInfo> userInfosPage = userRepository.findAll( new Specification<UserInfo>(){
            @Override
            public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate p1 = criteriaBuilder.equal(root.get("userName").as(String.class), userVO.getUserName());
//                Predicate p2 = criteriaBuilder.equal(root.get("userId").as(String.class), userVO.getUserId());
//                Predicate p3 = criteriaBuilder.equal(root.get("author").as(String.class), userVO.getUserName());
                query.where(criteriaBuilder.and(p1));
                return query.getRestriction();
            }
        },pageable);
        return userInfosPage;
    }
}
