package cn.com.taiji.security;

import cn.com.taiji.domain.Role;
import cn.com.taiji.domain.UserInfo;
import cn.com.taiji.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author : wangsuide
 * @version 1.0
 * @Project: project-practice01
 * @Package cn.com.taiji.security
 * @Description: TODO
 * @date Date : 2018年12月16日 13:48
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    private UserInfo userInfo = null;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Pattern email = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Pattern phone = Pattern.compile("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$");

        if (email.matcher(username).matches()) {
             userInfo = userService.findByUserEmail(username);
        }else if (phone.matcher(username).matches()){
             userInfo = userService.findByUserPhone(username);
        }else {
             userInfo = userService.findByUserLoginName(username);
        }

            //通过用户名查询用户信息保存到userInfo
            if (userInfo == null && userInfo.getUserStatus() == "0") {
                //判断查询到的用户是否存在，不存在则抛UsernameNotFoundException异常
                throw new UsernameNotFoundException(username + "该用户不存在！");
            }

            //把当前用户信息保存到session中
            List<GrantedAuthority> authoritys = new ArrayList<>();
            //得到用户的角色信息
            List<Role> roles = userInfo.getRolesList();
            for (Role role : roles) {
                //把得到的信息保存到GrantedAuthority对象中
                GrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName());
                authoritys.add(authority);
            }

        if (email.matcher(username).matches()) {
            //把得到的邮箱，密码，以及角色名字保存到UserDetails对象中
            UserDetails user = new User(userInfo.getUserEmail(), userInfo.getUserPassword(), authoritys);
            return user;
        }else if (phone.matcher(username).matches()){
            //把得到的手机号，密码，以及角色名字保存到UserDetails对象中
            UserDetails user = new User(userInfo.getUserPhone(), userInfo.getUserPassword(), authoritys);
            return user;
        }else {
            //把得到的用户名，密码，以及角色名字保存到UserDetails对象中
            UserDetails user = new User(userInfo.getUserLoginName(), userInfo.getUserPassword(), authoritys);
            return user;
        }
    }
}
