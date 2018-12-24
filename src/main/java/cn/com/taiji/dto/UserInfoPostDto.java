package cn.com.taiji.dto;

import cn.com.taiji.domain.Groups;
import cn.com.taiji.domain.Posts;
import cn.com.taiji.domain.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName UserInfoPostDto
 * @Description
 * @Author --zhengwei
 * @Date 2018/12/23 18:19
 * @Version
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoPostDto {

    private UserInfo userIn;
    private Posts posts;
}
