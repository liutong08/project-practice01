package cn.com.taiji.service;

import org.springframework.security.access.ConfigAttribute;

import cn.com.taiji.domain.Permission;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author : wangsuide
 * @version 1.0
 * @Project: project-practice01
 * @Package cn.com.taiji.service
 * @Description: TODO
 * @date Date : 2018年12月16日 13:48
 */

public interface PermissionService {

    Map<String, Collection<ConfigAttribute>> getPermissionMap();

    Integer savepermission(Integer permissionId, Integer roleId);

    Integer deletepermission(Integer permissionId, Integer roleId);

    List<Permission> findAll();

    Permission save(Permission permission);

    Permission findByPermissionId(Integer permissionId);

    boolean updatePermissionById(Permission permission);

    Integer deletepermissionById(Integer permissionId);

}
