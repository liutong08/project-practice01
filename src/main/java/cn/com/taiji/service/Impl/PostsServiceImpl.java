package cn.com.taiji.service.Impl;

import cn.com.taiji.domain.Posts;
import cn.com.taiji.repository.PostsRepository;
import cn.com.taiji.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    //查询贴子的全部信息以及发帖人信息，回帖信息，回帖人信息
    @Transactional()
    @Override
    public Posts findPostById(Integer id) {
        Posts post=postsRepository.findOne(id);
        post.getRepliesList();
        post.getUserInfo();
        post.getGroups();
        return post;
    }
}
