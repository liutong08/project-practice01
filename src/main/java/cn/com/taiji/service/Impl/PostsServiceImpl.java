package cn.com.taiji.service.Impl;

import cn.com.taiji.domain.Posts;
import cn.com.taiji.domain.UserInfo;
import cn.com.taiji.repository.PostsRepository;
import cn.com.taiji.repository.UserRepository;
import cn.com.taiji.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ Author     ：liutong.
 * @ Date       ：Created in 22:54 2018/12/17
 * @ Description：${description}
 * @ Modified By：
 * @ Version:     $version
 */
@Service
public class PostsServiceImpl implements PostsService {
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private UserRepository userRepository;


    //查询贴子的全部信息以及发帖人信息，回帖信息，回帖人信息
    @Transactional()
    @Override
    public Posts findPostById(Integer id) {
        Posts post = postsRepository.findOne(id);
        post.getRepliesList();
        post.getUserInfo();
        post.getGroups();
        return post;
    }

    //添加讨论组内贴子
    @Transactional
    @Override
    public Posts addPost(Posts post) {
        Posts save = postsRepository.save(post);
        return save;
    }

//    //通过GrouId查询当前讨论组的所有贴子
//    @Transactional()
//    @Override
//    public List<Posts> findPostsByGroupId(Integer id) {
//        return null;
//    }

    //删除贴子
    @Transactional()
    @Override
    public int deletePostByPostId(Integer id) {
        int i = postsRepository.deleteByPostId(id);
        return i;
    }

    @Override
    public List<Posts> findAllPosts() {
        List<Posts> postsList = postsRepository.findAll();
        return postsList;
    }

    @Override
    public void deletePostsList(List<Posts> postsList) {
        postsRepository.delete(postsList);
    }

    @Override
    public List<Posts> findPostsByUserId(Integer id) {
        UserInfo userInfo = userRepository.findByUserId(id);
        List<Posts> postsList = userInfo.getPostsList();
        return postsList;
    }
}
