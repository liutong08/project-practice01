package cn.com.taiji.security.validate.vaildateCode;

import org.springframework.security.core.AuthenticationException;

/**
 * @author : wangsuide
 * @version 1.0
 * @Project: project-practice01
 * @Package cn.com.taiji.security.validate.vaildateCode
 * @Description:
 * @date Date : 2018年12月16日 13:48
 */
public class ValidateCodeException extends AuthenticationException {

    //序列化时为了保持版本的兼容性，即在版本升级时反序列化仍保持对象的唯一性。
    private static final long serialVersionUID = 5022575393500654458L;

    public ValidateCodeException(String message) {
        super(message);
    }

}
