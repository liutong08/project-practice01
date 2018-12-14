package cn.com.taiji.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * @ClassName Permission
 * @Description
 * @Author --zhengwei
 * @Date 2018/12/14 16:38
 * @Version
 */
@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name="sys_permission")
public class Permission {
    @Id
    @GeneratedValue
    @Column(name="permission_id",length = 50)
    //权限ID，主键
    private Integer permissionId;
    //权限名
    @Column(name="permission_name",length = 50,nullable = false)
    private String permissionName;
    //权限路劲
    @Column(name="permission_url",length = 255,nullable = false)
    private String permissionUrl;
    //权限图标
    @Column(name="permission_icon",length = 255,nullable = false)
    private String  permissionIcon;

    //权限描述
    @Column(name="permission_description",length = 255)
    private String permissionDescription;
    //预留字段
    @Column(name = "permission_xx")
    private String permissionXx;
    @Column(name = "permission_yy")
    private String permissionYy;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "sys_permission_role"
            , joinColumns = { @JoinColumn(name = "permission_id") }
            , inverseJoinColumns = {@JoinColumn(name = "role_id") })
    private List<Role> roles;
}
