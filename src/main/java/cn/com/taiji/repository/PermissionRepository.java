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

    //给角色增加权限
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO sys_permissions_roles VALUES(?1, ?2)", nativeQuery = true)
    int savepermission(Integer permissionId, Integer roleId);


    //给角色删除权限
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM sys_permissions_roles WHERE permission_id = ?1 and role_id = ?2", nativeQuery = true)
    int deletepermission(Integer permissionId, Integer roleId);

    //新增加权限信息
    Permission save(Permission permission);

    //查找单个权限信息
    Permission findByPermissionId(Integer permissionId);

    //删除权限信息
    Integer deleteByPermissionId(Integer permissionId);
}
