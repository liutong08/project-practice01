package cn.com.taiji.dto;

import cn.com.taiji.domain.Replies;
import cn.com.taiji.domain.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ Author     ：liutong.
 * @ Date       ：Created in 23:08 2018/12/20
 * @ Description：${description}
 * @ Modified By：
 * @ Version:     $version
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyUserDto {
    private Replies replies;
    private UserInfo userInfo;

    @Override
    public String toString() {
        return "ReplyUserDto{" +
                "replies=" + replies +
                ", userInfo=" + userInfo +
                '}';
    }
}
