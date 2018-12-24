package cn.com.taiji;

//import cn.com.taiji.service.PermissionService;
import cn.com.taiji.service.RoleService;
import cn.com.taiji.service.UserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author : wangsuide
 * @version 1.0
 * @Project: project-practice01
 * @Package cn.com.taiji
 * @Description: TODO
 * @date Date : 2018年12月16日 13:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectPractice01ApplicationTests {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
//    @Autowired
//    private PermissionService permissionService;

    @Test
    public void contextLoads() {
        Integer[] lists ={1,2,3};
        for(Integer role : lists){
            System.out.println("1:"+role);
//            int num = userService.saveroles(1,role);
        }
//        permissionService.saveABRelation(1,1);
    }
}

