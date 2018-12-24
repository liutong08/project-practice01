package cn.com.taiji.dto;

import cn.com.taiji.domain.Blogs;
import cn.com.taiji.domain.Groups;
import cn.com.taiji.domain.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : wangsuide
 * @version 1.0
 * @Project: blog
 * @Package cn.com.taiji.dto
 * @Description: TODO
 * @date Date : 2018年12月21日 23:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogUserDto {
    private Blogs blog;
    private UserInfo userInfo;

    @Override
    public String toString() {
        return "BlogUserDto{" +
                "blog=" + blog +
                ", userInfo=" + userInfo +
                '}';
    }
}
