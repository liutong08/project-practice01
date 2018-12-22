package cn.com.taiji.service;

import java.util.List;

import cn.com.taiji.domain.Blogs;
import cn.com.taiji.domain.Groups;
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
//	分页
	Page<Blogs> findBlogsNoCriteria(Integer page, Integer size);

}
