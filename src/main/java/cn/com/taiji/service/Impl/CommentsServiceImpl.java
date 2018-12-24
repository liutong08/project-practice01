package cn.com.taiji.service.Impl;

import cn.com.taiji.domain.Comments;
import cn.com.taiji.repository.CommentsRepository;
import cn.com.taiji.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName CommentsServiceImpl
 * @Description
 * @Author --zhengwei
 * @Date 2018/12/22 18:22
 * @Version
 */
@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentsRepository commentsRepository;
    //添加评论
    @Override
    public boolean save(Comments comments) {
        return commentsRepository.save(comments)!=null? true:false;
    }
}
