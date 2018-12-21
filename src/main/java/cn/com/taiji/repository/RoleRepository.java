package cn.com.taiji.repository;

import cn.com.taiji.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * @author : wangsuide
 * @version 1.0
 * @Project: project-practice01
 * @Package cn.com.taiji.repository
 * @Description: TODO
 * @date Date : 2018年12月16日 13:48
 */
public interface RoleRepository extends JpaRepository<Role,Integer> {

int deleteByRoleId(Integer RoleId);



}
