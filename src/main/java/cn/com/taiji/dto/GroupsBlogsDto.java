package cn.com.taiji.dto;

import cn.com.taiji.domain.Blogs;
import cn.com.taiji.domain.Groups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName GroupsBlogsDto
 * @Description
 * @Author --zhengwei
 * @Date 2018/12/22 12:13
 * @Version
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupsBlogsDto {

    private Blogs blogs;
    private Groups groups;
}
