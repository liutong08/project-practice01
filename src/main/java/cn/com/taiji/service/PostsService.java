package cn.com.taiji.service;

import cn.com.taiji.domain.Posts;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * @ Author     ：liutong.
 * @ Date       ：Created in 22:54 2018/12/17
 * @ Description：${description}
 * @ Modified By：
 * @ Version:     $version
 */
public interface PostsService {

    public Posts findPostById(Integer id);
    public Posts addPost(Posts post);
    //    public List<Posts> findPostsByGroupId(Integer id);
    public int deletePostByPostId(Integer id);
    public List<Posts> findAllPosts();
    public void deletePostsList(List<Posts> postsList);
}
