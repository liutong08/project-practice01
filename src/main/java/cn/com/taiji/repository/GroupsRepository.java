package cn.com.taiji.repository;

import cn.com.taiji.domain.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ Author     ：liutong.
 * @ Date       ：Created in 16:35 2018/12/17
 * @ Description：${description}
 * @ Modified By：
 * @ Version:     $version
 */
public interface GroupsRepository extends JpaRepository<Groups,Integer> {

   @Query("select g from Groups g  where g.groupStatus='1'")
    List<Groups> findAllByGroupStatus();
}
