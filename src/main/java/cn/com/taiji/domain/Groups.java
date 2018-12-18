package cn.com.taiji.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @ Author     ：liutong.
 * @ Date       ：Created in 9:19 2018/12/17
 * @ Description：讨论组基本信息的数据表设计
 * @ Modified By：
 * @ Version:     $version
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_groups")
//讨论组
public class Groups {

    //讨论组ID,主键
    @Id
    @GeneratedValue
    @Column(name = "group_id", length = 11, nullable = false)
    private Integer groupId;

    //讨论组名
    @Column(name = "group_name", length = 50, nullable = false)
    private String groupName;

    //讨论组创建时间
    @Column(name = "group_create_name", nullable = false)
    private Date groupCreateTime;

    //讨论组图标
    @Column(name = "group_ico", length = 255)
    private String groupIco;

    //讨论组状态
    @Column(name = "group_status", length = 5)
    private String groupStatus;

    //讨论组描述
    @Column(name = "group_description", length = 255)
    private String groupDescription;

    //讨论组组长
    @JsonIgnore
    @JoinColumn(name = "user_id")
    @ManyToOne
    private UserInfo userInfo;

    //备用字段XX
    @Column(name = "group_xx", length = 255)
    private String groupXx;

    //备用字段YY
    @Column(name = "group_yy", length = 255)
    private String groupYy;

    //创建groups和userInfo的中间表讨论组成员表，关系为ManyToMany
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "sys_groups_users"
            , joinColumns = {@JoinColumn(name = "group_id")}
            , inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<UserInfo> userInfoList;

    //创建groups和labels的中间表讨论组标签表，关系为ManyToMany
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "sys_groups_labels"
            , joinColumns = {@JoinColumn(name = "group_id")}
            , inverseJoinColumns = {@JoinColumn(name = "label_id")})
    private List<Labels> labelsList;

    //创建groups和posts的联系，关系为OneToMany
    @JsonIgnore
    // @JoinColumn(name = "group_id")
    @OneToMany(mappedBy = "groups")
    private List<Posts> postsList;

    @Override
    public String toString() {
        return "Groups{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", groupCreateTime=" + groupCreateTime +
                ", groupIco='" + groupIco + '\'' +
                ", groupStatus='" + groupStatus + '\'' +
                ", groupDescription='" + groupDescription + '\'' +
                ", groupXx='" + groupXx + '\'' +
                ", groupYy='" + groupYy + '\'' +
                '}';
    }
}
