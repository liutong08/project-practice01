package cn.com.taiji.security.validate.vaildateCode;

import cn.com.taiji.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author : wangsuide
 * @version 1.0
 * @Project: project-practice01
 * @Package cn.com.taiji.security.validate.vaildateCode
 * @Description:
 * @date Date : 2018年12月16日 13:48
 */
@Component
public class ValidateAuthenticationConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private CustomUserDetailsService customUserService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ValidateAuthenticationFilter validateAuthenticationFilter = new ValidateAuthenticationFilter();
        validateAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        validateAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        validateAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

        ValidateAuthenticationProvider smsAuthenticationProvider = new ValidateAuthenticationProvider();
        smsAuthenticationProvider.setUserDetailService(customUserService);
        http.authenticationProvider(smsAuthenticationProvider)
                .addFilterAfter(validateAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
