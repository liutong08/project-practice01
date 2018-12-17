package cn.com.taiji.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * @ Author     ：liutong.
 * @ Date       ：Created in 10:11 2018/12/17
 * @ Description：讨论组回帖信息的数据表设计
 * @ Modified By：
 * @ Version:     $version
 */
@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_replies")
//讨论组
public class Replies {

    //回帖ID，主键
    @Id
    @GeneratedValue
    @Column(name = "reply_id", length = 11, nullable = false)
    private Integer replyId;

    //回帖内容
    @Column(name = "reply_content", length = 1024, nullable = false)
    private String replyContent;

    //回帖时间
    @Column(name = "reply_create_time", nullable = false)
    private Date replyCreateTime;

    //备用字段XX
    @Column(name = "reply_xx", length = 255)
    private String replyXx;

    //备用字段YY
    @Column(name = "reply_yy", length = 255)
    private String replyYy;

    //创建replies和posts的联系，关系为ManyToOne
    @JoinColumn(name = "posts_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Posts posts;

    //创建replies和user的联系，关系为ManyToOne
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserInfo userInfo;

}
