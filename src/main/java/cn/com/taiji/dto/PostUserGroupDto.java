package cn.com.taiji.dto;

import cn.com.taiji.domain.Groups;
import cn.com.taiji.domain.Posts;
import cn.com.taiji.domain.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ Author     ：liutong.
 * @ Date       ：Created in 15:43 2018/12/19
 * @ Description：${description}
 * @ Modified By：
 * @ Version:     $version
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUserGroupDto {
    private Posts posts;
    private UserInfo userInfo;
    private Groups groups;

    @Override
    public String toString() {
        return "PostUserGroupDto{" +
                "posts=" + posts +
                ", userInfo=" + userInfo +
                ", groups=" + groups +
                '}';
    }
}
