package cn.com.taiji.repository;

import java.util.List;

import cn.com.taiji.domain.Groups;
import cn.com.taiji.domain.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import cn.com.taiji.domain.Blogs;
import cn.com.taiji.domain.Labels;

public interface BlogsRepository extends JpaRepository <Blogs,Integer> , JpaSpecificationExecutor<Blogs> {



//	@Transactional
//	@Modifying
//	@Query("Select Blogs  from Blogs b WHERE b.userId =: userId")
//	List<Blogs> findAllByUserId(@Param("userId") Integer userId);
//
//	@Transactional
//	@Modifying
//	@Query("Select Blogs  FROM Blogs b,Labels l,sys_blogs_labels s WHERE b.blogId = s.blogId AND l.labelId = s.labelId")
//	List<Blogs> getAllBlogByLabel(@Param("label") Labels label);
//
//	@Transactional
//	@Modifying
//	@Query("Select Blogs b WHERE b.blogId =: blogId")
//	Blogs getBlogByBlogId(@Param("blogId") Integer blogId);

    List<Blogs> findByBlogStatus(String num);
//    真删除
@Transactional
@Modifying
@Query(value = "DELETE  FROM Blogs b WHERE b.blogId =?1")
int deleteByBlogId(Integer blogId);

@Query(value = "select sb.* from sys_blogs sb  left join  sys_blogs_labels  sbl on  sb.blog_id = sbl.blog_id  left join sys_labels sl on sbl.label_id = sl.label_id  where sl.label_id = :labelId  ORDER BY ?#{#pageable}",
        countQuery = "select count(*) from sys_blogs sb left join sys_blogs_labels sbl on sb.blog_id = sbl.blog_id   left join sys_labels sl on sbl.label_id = sl.label_id where sl.label_id = :labelId",
        nativeQuery = true)
Page<Blogs> blogsAndLabels(@Param("labelId") Integer labelId, Pageable pageable);

}
