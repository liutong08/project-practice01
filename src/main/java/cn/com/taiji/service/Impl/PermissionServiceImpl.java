package cn.com.taiji.service.Impl;

import cn.com.taiji.domain.Permission;
import cn.com.taiji.domain.Role;
import cn.com.taiji.repository.PermissionRepository;
import cn.com.taiji.service.PermissionService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Map<String, Collection<ConfigAttribute>> getPermissionMap() {
        if (permissionMap == null || permissionMap.size() == 0) {
            initPermissions();
        }
        return permissionMap;
    }

    @PostConstruct
    @Transactional
    public void initPermissions() {
        //程序启动后，立即自动执行
        //初始化permissionMap
        //key =  ulr
        //value = [role1,role2] ;
        permissionMap = new HashMap();
        List<Permission> permissions = permissionRepository.findAll();

        for (Permission p : permissions) {
            Collection<ConfigAttribute> collection = new ArrayList<ConfigAttribute>();

            for (Role role : p.getRolesList()) {
                ConfigAttribute cfg = new SecurityConfig(role.getRoleName());
                collection.add(cfg);
            }
            //构造k,v的map,k是url,v是role的名字不是permission的名字，用来和用户拥有的role名字比对
            permissionMap.put(p.getPermissionUrl(), collection);
        }
    }

    //修改角色和权限的关系
    @Transactional
    @Override
    public Integer savepermission(Integer permissionId, Integer roleId) {

        Integer num = permissionRepository.savepermission(permissionId,roleId);
        if (num != 0){
            initPermissions();
            return num;
        }else {
            return num;
        }
    }

    //删除角色和权限的关系
    @Transactional
    @Override
    public Integer deletepermission(Integer permissionId, Integer roleId) {

        Integer num = permissionRepository.deletepermission(permissionId,roleId);
        if (num != 0){
            initPermissions();
            return num;
        }else {
            return num;
        }
    }

    @Override
    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    //保存权限
    @Transactional
    @Override
    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }


    @Override
    public Permission findByPermissionId(Integer permissionId) {
        return permissionRepository.findByPermissionId(permissionId);
    }

    //更新权限信息
    @Transactional
    @Override
    public boolean updatePermissionById(Permission permission) {

        Permission permission1 = permissionRepository.findByPermissionId(permission.getPermissionId());
        BeanUtils.copyProperties(permission,permission1 );
        Permission permission2=  permissionRepository.saveAndFlush(permission1);
        if (permission2 != null){
            return true;
        }else{
            return false;
        }
    }

    @Transactional
    @Override
    public Integer deletepermissionById(Integer permissionId) {
        return permissionRepository.deleteByPermissionId(permissionId);
    }
}
