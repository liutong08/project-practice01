package cn.com.taiji.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * @ClassName Labels
 * @Description
 * @Author --zhengwei
 * @Date 2018/12/17 9:45
 * @Version
 */
@Entity
@Table(name="sys_labels")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
//标签表
public class Labels {

    //标签ID，主键
    @Id
    @GeneratedValue
    @Column(name = "label_id",length = 11)
    private Integer  labelId;

    //标签名
    @Column(name = "label_name",length = 50)
    private String labelName;

    //标签描述
    @Column(name = "label_description",length = 255)
    private String labelDescription;

    //预留字段
    @Column(name = "label_xx",length = 255)
    private String labelXx;
    @Column(name = "label_yy",length =255)
    private String labelYy;

}
