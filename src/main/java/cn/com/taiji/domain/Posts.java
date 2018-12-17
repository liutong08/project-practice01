package cn.com.taiji.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @ Author     ：liutong.
 * @ Date       ：Created in 9:43 2018/12/17
 * @ Description：讨论组发帖信息的数据表设计
 * @ Modified By：
 * @ Version:     $version
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_posts")
//讨论组发帖
public class Posts {

    //发帖ID，主键
    @Id
    @GeneratedValue
    @Column(name = "post_id", length = 11, nullable = false)
    private Integer postId;

    //发帖标题
    @Column(name = "post_title", length = 50, nullable = false)
    private String postTitle;

    //发帖内容
    @Column(name = "post_content", length = 1024, nullable = false)
    private String postContent;

    //发帖时间
    @Column(name = "post_create_time", nullable = false)
    private Date postCreateTime;

    //发帖图片路径
    @Column(name = "post_pic", length = 255)
    private String postPic;

    //备用字段XX
    @Column(name = "post_xx", length = 255)
    private String postXx;

    //备用字段YY
    @Column(name = "post_yy", length = 255)
    private String postYy;

    //创建posts和groups的联系，关系为ManyToOne
    @JoinColumn(name = "group_id")
    @ManyToOne()
    private Groups groups;

    //创建posts和replies的联系，关系为OneToMany
    @OneToMany(mappedBy = "posts")
    private List<Replies> repliesList;

    //创建posts和user的联系，关系为ManyToOne
    @JoinColumn(name = "user_id")
    @ManyToOne
    private UserInfo userInfo;

    @Override
    public String toString() {
        return "Posts{" +
                "postId=" + postId +
                ", postTitle='" + postTitle + '\'' +
                ", postContent='" + postContent + '\'' +
                ", postCreateTime=" + postCreateTime +
                ", postPic='" + postPic + '\'' +
                ", postXx='" + postXx + '\'' +
                ", postYy='" + postYy + '\'' +
                '}';
    }
}
