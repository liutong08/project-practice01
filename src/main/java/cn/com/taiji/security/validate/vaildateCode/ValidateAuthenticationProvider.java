package cn.com.taiji.security.validate.vaildateCode;

import cn.com.taiji.security.CustomUserDetailsService;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author : wangsuide
 * @version 1.0
 * @Project: project-practice01
 * @Package cn.com.taiji.security.validate.vaildateCode
 * @Description:
 * @date Date : 2018年12月16日 13:48
 */
public class ValidateAuthenticationProvider implements AuthenticationProvider {

    private CustomUserDetailsService customUserService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ValidateAuthenticationToken authenticationToken = (ValidateAuthenticationToken) authentication;
        //把拦截的邮箱或手机号传输给CustomUserDetailsService里的loadUserByUsername
        UserDetails userDetails = customUserService.loadUserByUsername((String) authenticationToken.getPrincipal());

        if (userDetails == null)
            throw new InternalAuthenticationServiceException("未找到与该邮箱对应的用户");
        ValidateAuthenticationToken authenticationResult = new ValidateAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return ValidateAuthenticationToken.class.isAssignableFrom(aClass);
    }

    public CustomUserDetailsService getUserDetailService() {
        return customUserService;
    }

    public void setUserDetailService(CustomUserDetailsService customUserService) {
        this.customUserService = customUserService;
    }
}
