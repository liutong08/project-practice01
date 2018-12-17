package cn.com.taiji.service;

import org.springframework.security.access.ConfigAttribute;

import java.util.Collection;
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

    int saveABRelation(Integer permissionId, Integer roleId);
}
