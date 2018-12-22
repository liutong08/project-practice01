package cn.com.taiji.repository;

import java.util.List;

import cn.com.taiji.domain.Groups;
import cn.com.taiji.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import cn.com.taiji.domain.Blogs;
import cn.com.taiji.domain.Labels;

public interface BlogsRepository extends JpaRepository <Blogs,Integer> {



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

}
