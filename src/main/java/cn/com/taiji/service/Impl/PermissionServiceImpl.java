package cn.com.taiji.service.Impl;

import cn.com.taiji.domain.Permission;
import cn.com.taiji.domain.Role;
import cn.com.taiji.repository.PermissionRepository;
import cn.com.taiji.service.PermissionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author : wangsuide
 * @version 1.0
 * @Project: project-practice01
 * @Package cn.com.taiji.service.Impl
 * @Description: TODO
 * @date Date : 2018年12月16日 13:48
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    private Map<String, Collection<ConfigAttribute>> permissionMap = null;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Map<String, Collection<ConfigAttribute>> getPermissionMap() {
        if (permissionMap == null || permissionMap.size() == 0) {
            initPermissions();
        }
        return permissionMap;
    }

    @PostConstruct
    public void initPermissions() {
        //程序启动后，立即自动执行
        //初始化permissionMap
        //key =  ulr
        //value = [role1,role2] ;
        permissionMap = new HashMap();
        List<Permission> permissions = permissionRepository.findAll();
        for (Permission p : permissions) {
            Collection<ConfigAttribute> collection = new ArrayList<ConfigAttribute>();
            for (Role role : p.getRoles()) {
                ConfigAttribute cfg = new SecurityConfig(role.getRoleName());
                collection.add(cfg);
            }
            //构造k,v的map,k是url,v是role的名字不是permission的名字，用来和用户拥有的role名字比对
            permissionMap.put(p.getPermissionUrl(), collection);
        }
    }

    @Override
    public int saveABRelation(Integer permissionId, Integer roleId) {
        return permissionRepository.saveABRelation(permissionId, roleId);
    }
}
