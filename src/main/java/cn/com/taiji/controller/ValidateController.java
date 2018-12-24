package cn.com.taiji.controller;

import cn.com.taiji.security.validate.vaildateCode.ValidateCode;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : wangsuide
 * @version 1.0
 * @Project: project-practice01
 * @Package cn.com.taiji.controller
 * @Description: TODO
 * @date Date : 2018年12月16日 19:23
 */
@Controller
@RequestMapping("/validate")
public class ValidateController {

    public final static String SESSION_KEY_SMS_CODE = "SESSION_KEY_SMS_CODE";
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Value("${mail.fromMail.sender}")
    private String sender;

    @Autowired
    private JavaMailSender javaMailSender;

    //务必要给此方法传入邮箱号
    @GetMapping("/code")
    public void createSmsCode(HttpServletRequest request,String mobile) {
        ValidateCode smsCode = createValidateCode();
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY_SMS_CODE, smsCode);
        // 输出验证码到控制台代替短信发送服务
        System.out.println("您的登录验证码为：" + smsCode.getCode() + "，有效时间为60秒");
        //建立邮件消息
        SimpleMailMessage mainMessage = new SimpleMailMessage();
        //发送者
        mainMessage.setFrom(sender);
        //接收者
        mainMessage.setTo(mobile);
        //发送的标题
        mainMessage.setSubject("太极计算机开发平台");
        //发送的内容
        mainMessage.setText("感谢您使用我公司的产品，您的登录验证码为：" + smsCode.getCode() + "，有效时间为60秒");
        javaMailSender.send(mainMessage);
    }

    private ValidateCode createValidateCode() {
        String code = RandomStringUtils.randomNumeric(6);
        return new ValidateCode(code, 100);
    }
}
