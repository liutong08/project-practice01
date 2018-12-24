package cn.com.taiji.service;

import java.awt.print.Pageable;
import java.util.List;

import cn.com.taiji.domain.Blogs;
import cn.com.taiji.domain.Groups;
import cn.com.taiji.domain.Labels;
import org.springframework.data.domain.Page;

public interface BlogsService {

	boolean saveBlog(Blogs blog);
	boolean updateBlog(Blogs blog);
	List<Blogs> getAllBlog();
//	List<Blogs> getAllBlogByUserId(Integer UserId);
//	List<Blogs> getAllBlogByLabel(List<Labels> labels);
	Blogs getBlogByBlogId(Integer blogId);

	List<Blogs> findByBlogStatus(String num);

	public List<Blogs> findAllBlogs();
//	分页,
	Page<Blogs> findBlogsNoCriteria(Integer page, Integer size);
	Page<Blogs> findBlogsCriteria(Integer labelId,Integer page);
////	真删除
boolean deleteByBlogId(Integer blogId);
}
