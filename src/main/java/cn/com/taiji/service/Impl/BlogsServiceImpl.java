package cn.com.taiji.service.Impl;

import java.util.ArrayList;
import java.util.List;



import cn.com.taiji.domain.Labels;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.com.taiji.domain.Blogs;
import cn.com.taiji.repository.BlogsRepository;
import cn.com.taiji.service.BlogsService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class BlogsServiceImpl implements BlogsService{

	@Autowired
	BlogsRepository blogsRepository;

	@Override
	@Transactional
	public boolean saveBlog(Blogs blog) {
		Blogs bo = blogsRepository.save(blog);
		if(bo != null) {
			return true;
		}else {
			return false;
		}
	}

	@Transactional
	@Override
	public boolean updateBlog(Blogs blogs) {
		Blogs old= blogsRepository.findOne(blogs.getBlogId());
//        userInfo.preUpdate();
		//本段代码可以代替get，set，不用一个一个赋值
		BeanUtils.copyProperties(blogs, old);
		Blogs blog =  blogsRepository.saveAndFlush(old);
		if (blog != null){
			return true;
		}else{
			return false;
		}
	}



	@Override
	public List<Blogs> getAllBlog() {
		List<Blogs> listBlogs = blogsRepository.findAll();
		for(Blogs blogs : listBlogs){
			if (blogs.getBlogStatus().equals("0")){
				listBlogs.remove(blogs);
			}
		}
		if(listBlogs.get(0) != null) {
			return listBlogs;
		}else {
			return null;
		}

	}

	@Transactional
	@Override
	public Blogs getBlogByBlogId(Integer blogId) {
		Blogs blogs =  blogsRepository.findOne(blogId);
		blogs.getCommentsList();
		if(blogs != null) {
			return blogs;
		}else {
			return null;
		}
	}

//	查询状态为1的博客
	@Override
	public List<Blogs> findByBlogStatus(String num) {
		List<Blogs> listBlogs = blogsRepository.findByBlogStatus(num);
		if(listBlogs != null && listBlogs.size() > 0){
			return listBlogs;
		}else{
			return  listBlogs;
		}
	}
	//查询所有博客
	@Transactional()
	@Override
	public List<Blogs> findAllBlogs() {
		List<Blogs> blogsList = blogsRepository.findAll();
		return blogsList;
	}


	@Override
	public Page<Blogs> findBlogsCriteria(Integer labelId,Integer page) {
		Pageable pageable = new PageRequest(page, 5, Sort.Direction.ASC, "blog_id");
		Page<Blogs> blogs = blogsRepository.blogsAndLabels(labelId, pageable);
		return blogs;
	}

	//分页查询所有
	@Override
	public Page<Blogs> findBlogsNoCriteria(Integer page, Integer size) {
		Pageable pageable = new PageRequest(page, size, Sort.Direction.ASC, "blogId");
		return blogsRepository.findAll(pageable);
	}



	//真删除
	@Override
	public boolean deleteByBlogId(Integer blogId) {
		int i = blogsRepository.deleteByBlogId(blogId);
		if (i > 0 ){
			return true;
		}else{
			return false;
		}
	}

}
