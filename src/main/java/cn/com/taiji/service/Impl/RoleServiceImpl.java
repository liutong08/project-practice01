package cn.com.taiji.service.Impl;

import cn.com.taiji.repository.RoleRepository;
import cn.com.taiji.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : wangsuide
 * @version 1.0
 * @Project: project-practice01
 * @Package cn.com.taiji.service.Impl
 * @Description: TODO
 * @date Date : 2018年12月16日 20:06
 */

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role insertrole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Integer deleterole(Integer roleId) {
        return roleRepository.deleteByRoleId(roleId);
    }
}
