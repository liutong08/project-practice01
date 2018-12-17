package cn.com.taiji.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * @ClassName UserInfo
 * @Description ：用户表
 * @Author --zhengwei
 * @Date 2018/12/14 16:06
 * @Version
 */
@Entity
@Table(name = "sys_users")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    //用户id
    @Id
    @GeneratedValue
    @Column(name = "user_id", length = 50)
    private Integer userId;

    //用户真实姓名
    @Column(name = "user_name", length = 50, nullable = false)
    private String userName;

    //用户登录名
    @Column(name = "user_login_name", length = 50, nullable = false)
    private String userLoginName;

    //用户登录密码
    @Column(name = "user_password", length = 50, nullable = false)
    private String userPassword;

    //用户电话
    @Column(name = "user_phone", length = 20)
    private String userPhone;

    //用户邮箱
    @Column(name = "user_email", length = 50)
    private String userEmail;

    //用户性别
    @Column(name = "user_gender", length = 5)
    private String userGender;

    //用户头像
    @Column(name = "user_pic", length = 255)
    private String userPic;

    //用户状态（实现假删除）
    @Column(name = "user_status", length = 2)
    private String userStatus;

    //用户年龄
    @Column(name = "user_age", length = 2)
    private Integer userAge;

    //用户地址
    @Column(name = "user_address", length = 50)
    private String userAddress;

    //用户QQ
    @Column(name = "user_qq", length = 20)
    private String userQq;
    //预留字段

    @Column(name = "user_xx", length = 255)
    private String userXx;

    //预留字段
    @Column(name = "user_yy", length = 255)
    private String userYy;

    //用户和角色多对多关系
    @ManyToMany(cascade={CascadeType.ALL},fetch = FetchType.LAZY)
    @JoinTable(name = "sys_users_roles"
            , joinColumns = {@JoinColumn(name = "user_id")}
            , inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> rolesList;

    //用户表与博客表是一对多的关系
    @JsonIgnore
    @OneToMany(mappedBy = "userInfo",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Blogs> blogsList;

    //用户与评论是一对多关系
    @JsonIgnore
    @OneToMany(mappedBy = "userInfo",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Comments> commentsList;
    //用户与帖子是一对多关系
    @JsonIgnore
    @OneToMany(mappedBy = "userInfo",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Posts> postsList;
    //用户与回帖是一对多关系
    @JsonIgnore
    @OneToMany(mappedBy = "userInfo",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Replies> repliesList;
}
