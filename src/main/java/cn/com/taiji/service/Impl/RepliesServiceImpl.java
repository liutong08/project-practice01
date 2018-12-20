package cn.com.taiji.service.Impl;

import cn.com.taiji.domain.Posts;
import cn.com.taiji.domain.Replies;
import cn.com.taiji.repository.RepliesRepository;
import cn.com.taiji.service.RepliesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ Author     ：liutong.
 * @ Date       ：Created in 23:33 2018/12/18
 * @ Description：${description}
 * @ Modified By：
 * @ Version:     $version
 */
@Service
public class RepliesServiceImpl implements RepliesService {

    @Autowired
    private RepliesRepository repliesRepository;

    //查询所有回帖
    @Transactional()
    @Override
    public List<Replies> findAllReplies() {
        List<Replies> repliesList=repliesRepository.findAll();
        return repliesList;
    }

    //查询指定id的回帖
    @Transactional()
    @Override
    public Replies findReplyById(Integer id) {
        Replies reply = repliesRepository.findOne(id);
        return reply;
    }

    //添加回帖
    @Transactional()
    @Override
    public Replies addReply(Replies replies) {
        Replies save = repliesRepository.save(replies);
        return save;
    }

    //删除回帖
    @Transactional()
    @Override
    public int deleteReplyByReplyId(Integer id) {
        int i = repliesRepository.deleteByReplyId(id);
        return i;
    }

    //批量删除ReplyList
    @Transactional
    @Override
    public void deleteReplyList(List<Replies> repliesList) {
        repliesRepository.delete(repliesList);
    }

}
