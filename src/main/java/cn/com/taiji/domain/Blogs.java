package cn.com.taiji.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @ClassName Blogs
 * @Description : //博客表
 * @Author --zhengwei
 * @Date 2018/12/17 9:14
 * @Version
 */

@Entity
@Table(name="sys_blogs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blogs {
    @Id
    @GeneratedValue
    @Column(name="blog_id",length = 11)
    //博客表id，主键
    private Integer blogId;

    //博客标题
    @Column(name="blog_title",length = 50,nullable = false)
    private String blogTitle;

    //博客内容
    @Column(name="blog_content",length = 1024,nullable = false)
    private String blogContent;

    //博客发表时间
    @Column(name="blog_create_time",nullable = false)
    private Date blogCreateTime;

    //博客图片
    @Column(name="blog_pic",length = 255)
    private String  blogPic;

    //博客阅读量
    @Column(name="blog_read_num",length =11)
    private Integer blogReadNum;

    //博客点赞量
    @Column(name="blog_like_num",length = 11)
    private Integer blogLikeNum;

    //博客状态
    @Column(name="blog_status",length = 5)
    private String blogStatus;

    //预留字段
    @Column(name="blog_xx",length = 255)
    private String blogXx;
    @Column(name="blog_yy",length = 255)
    private String blogYy;

    //博客和评论表是多对多关系
    @ManyToMany
    @JoinTable(name = "sys_blogs_labels"
            , joinColumns = {@JoinColumn(name = "blog_id")}
            , inverseJoinColumns = {@JoinColumn(name = "label_id")})
    private List<Labels> labels;


    //博客和用户是多对一关系
    @ManyToOne
    private UserInfo userInfo;


    //博客和评论是一对多关系
    @OneToMany(mappedBy = "blogs")
    private List<Comments> commentsList;

    @Override
    public String toString() {
        return "Blogs{" +
                "blogId=" + blogId +
                ", blogTitle='" + blogTitle + '\'' +
                ", blogContent='" + blogContent + '\'' +
                ", blogCreateTime=" + blogCreateTime +
                ", blogPic='" + blogPic + '\'' +
                ", blogReadNum=" + blogReadNum +
                ", blogLikeNum=" + blogLikeNum +
                ", blogStatus='" + blogStatus + '\'' +
                ", blogXx='" + blogXx + '\'' +
                ", blogYy='" + blogYy + '\'' +
                '}';
    }
}
