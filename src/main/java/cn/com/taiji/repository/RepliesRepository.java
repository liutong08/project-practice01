package cn.com.taiji.repository;

import cn.com.taiji.domain.Permission;
import cn.com.taiji.domain.Posts;
import cn.com.taiji.domain.Replies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @ Author     ：liutong.
 * @ Date       ：Created in 16:35 2018/12/17
 * @ Description：${description}
 * @ Modified By：
 * @ Version:     $version
 */
public interface RepliesRepository extends JpaRepository<Replies,Integer> {
    int deleteByReplyId(Integer id);
    List<Replies> findAllByPosts(Posts post);
}
