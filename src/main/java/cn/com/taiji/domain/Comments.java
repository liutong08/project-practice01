package cn.com.taiji.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @ClassName Comments
 * @Description
 * @Author --zhengwei
 * @Date 2018/12/17 10:08
 * @Version
 */
@Entity
@Table(name="sys_comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
//评论表
public class Comments {

    //评论表ID，主键
    @Id
    @GeneratedValue
    @Column(name = "comment_id",length = 11)
    private Integer commentId;

    //评论内容
    @Column(name = "comment_content",length = 1024,nullable = false)
    private String commentContent;

    //评论时间
    @Column(name = "comment_create_time", nullable = false)
    private Date commentCreateTime;

    //预留字段
    @Column(name = "comment_xx")
    private String commentXx;
    @Column(name = "comment_yy")
    private String commentYy;

    //评论和博客是多对一关系
    @JsonIgnore
    @ManyToOne(cascade=CascadeType.ALL,optional=false)
    private Blogs blogs;

    //评论和用户是多对一关系
    @JsonIgnore
    @ManyToOne(cascade=CascadeType.ALL,optional=false)
    private UserInfo userInfo;


}
