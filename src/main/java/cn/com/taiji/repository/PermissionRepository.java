package cn.com.taiji.repository;

import cn.com.taiji.domain.Permission;
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
public interface PermissionRepository extends JpaRepository<Permission,Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO sys_permission_role VALUES(?1, ?2)", nativeQuery = true)
    int saveABRelation(Integer permissionId, Integer roleId);
}
