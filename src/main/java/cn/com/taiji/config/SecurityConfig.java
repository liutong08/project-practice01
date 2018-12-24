package cn.com.taiji.config;

import cn.com.taiji.security.CustomFilterSecurityInterceptor;
import cn.com.taiji.security.CustomUserDetailsService;
import cn.com.taiji.security.validate.vaildateCode.ValidateAuthenticationConfig;
import cn.com.taiji.security.validate.vaildateCode.ValidateCodeFilter;
import cn.com.taiji.security.validate.vaildateHandler.ValidateAuthenticationFailureHandler;
import cn.com.taiji.security.validate.vaildateHandler.ValidateAuthenticationSucessHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author : wangsuide
 * @version 1.0
 * @Project: project-practice01
 * @Package cn.com.taiji.config
 * @Description: TODO
 * @date Date : 2018年12月16日 19:23
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService ;
    @Autowired
    private CustomFilterSecurityInterceptor customFilterSecurityInterceptor;

    @Autowired
    private ValidateAuthenticationSucessHandler authenticationSucessHandler;

    @Autowired
    private ValidateAuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    @Autowired
    private ValidateAuthenticationConfig validateAuthenticationConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(customFilterSecurityInterceptor, FilterSecurityInterceptor.class)
                   .authorizeRequests()
                   .antMatchers("/css/**", "/fonts/**","/assets/**","/layer/**","/js/**"
                           ,"/Reception_css/**","/images/**","/img/**","/Reception_font-awesome/**"
                           ,"/Reception_fonts/**","/Reception_js/**", "/login","/login-phone"
                           ,"/login-eamil","/validate/code","/","/index","/index/toIndex","/Blogs/findAnyBlogs/*"
                           ,"/frontGroup/groupsIndex","/user/findOneUser/*").permitAll()
                   .anyRequest()
                   .authenticated()
                .and()
                   .formLogin()
                    // 登录跳转 URL
                      .loginPage("/login")
                    // 处理表单登录 URL
                      .loginProcessingUrl("/login")
                      .failureUrl("/login-error").permitAll()
                    // 处理登录成功
                      .successHandler(authenticationSucessHandler)
                    // 处理登录失败
//                      .failureHandler(authenticationFailureHandler)
                //注销行为任意访问
                .and()
                    .logout()
                      .permitAll()
                .and()
                      .apply(validateAuthenticationConfig);
            http.csrf().disable();
            http.headers().frameOptions().sameOrigin();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

