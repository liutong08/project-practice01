package cn.com.taiji.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : wangsuide
 * @version 1.0
 * @Project: project-practice01
 * @Package cn.com.taiji.util
 * @Description: TODO
 * @date Date : 2018年12月16日 13:48
 */
public class Message {
    //状态码 1-成功   -1-失败
    private int code;
    //提示信息
    private String msg;
    //用户要返回给浏览器的数据
    private Map<String,Object> extend = new HashMap<String,Object>();
    //定义的成功方法
    public static Message success(String msg){
        Message result = new Message();
        result.setCode(1);
        result.setMsg(msg);
        return result;
    }
    //定义的失败方法
    public static Message fail(String msg){
        Message result = new Message();
        result.setCode(-1);
        result.setMsg(msg);
        return result;
    }

    //可以添加自定义数据，返回给浏览器
    public Message add(String key,Object value){
        this.getExtend().put(key,value);
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }
}
