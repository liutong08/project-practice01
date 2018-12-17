package cn.com.taiji.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * @author : wangsuide
 * @version 1.0
 * @Project: project-practice01
 * @Package cn.com.taiji.repository
 * @Description: TODO
 * @date Date : 2018年12月16日 13:48
 */
public interface UserRepository extends JpaRepository<UserInfo,Integer> {

    UserInfo findByUserLoginName(String username);

    UserInfo findByUserPhone(String username);

    UserInfo findByUserEmail(String username);

    //给用户增加角色
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO sys_user_role VALUES(?1, ?2)", nativeQuery = true)
    int saveroles(Integer userId, Integer roleId);

    //给用户删除角色
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM sys_user_role WHERE user_id = ?1 and role_id = ?2", nativeQuery = true)
    int deleteroles(Integer id, Integer role);
}
