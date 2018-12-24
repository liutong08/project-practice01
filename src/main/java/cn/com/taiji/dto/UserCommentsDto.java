package cn.com.taiji.dto;

import cn.com.taiji.domain.Comments;
import cn.com.taiji.domain.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName UserCommentsDto
 * @Description
 * @Author --zhengwei
 * @Date 2018/12/22 17:04
 * @Version
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCommentsDto {

    private UserInfo userInfo;
    private Comments comments;
}

