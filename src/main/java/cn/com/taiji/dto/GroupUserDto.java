package cn.com.taiji.dto;

import cn.com.taiji.domain.Groups;
import cn.com.taiji.domain.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ Author     ：liutong.
 * @ Date       ：Created in 16:03 2018/12/18
 * @ Description：${description}
 * @ Modified By：
 * @ Version:     $version
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupUserDto {
    private Groups group;
    private UserInfo userInfo;

    @Override
    public String toString() {
        return "GroupUserDto{" +
                "group=" + group +
                ", userInfo=" + userInfo +
                '}';
    }
}
