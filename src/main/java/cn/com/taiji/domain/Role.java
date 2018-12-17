package cn.com.taiji.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName Role
 * @Description  :用户角色管理表
 * @Author --zhengwei
 * @Date 2018/12/14 16:24
 * @Version
 */
@Entity
@Table(name="sys_roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Role {
    //角色id
    @Id
    @Column(name="role_id",length = 50)
    @GeneratedValue
    private Integer roleId;

    //角色名
    @Column(name="role_name",length = 50,nullable = false)
    private String roleName;

    //角色描述
    @Column(name = "role_description",length = 255)
    private String roleDescription;

    //角色创建时间
    @Column(name="role_create_date",length = 255)
    private Date roleCreateDate;

    //预留字段
    @Column(name="role_xx")
    private String roleXx;

    //预留字段
    @Column(name="role_yy")
    private String roleYy;
}
