package cn.com.taiji.repository;

import cn.com.taiji.domain.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ Author     ：liutong.
 * @ Date       ：Created in 22:53 2018/12/17
 * @ Description：${description}
 * @ Modified By：
 * @ Version:     $version
 */
public interface PostsRepository extends JpaRepository<Posts,Integer> {
    int deleteByPostId(Integer id);
}
