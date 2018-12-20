package cn.com.taiji.service;

import cn.com.taiji.domain.Posts;
import cn.com.taiji.domain.Replies;

import java.util.List;

/**
 * @ Author     ：liutong.
 * @ Date       ：Created in 23:33 2018/12/18
 * @ Description：${description}
 * @ Modified By：
 * @ Version:     $version
 */
public interface RepliesService {
    public List<Replies> findAllReplies();
    public Replies findReplyById(Integer id);
    public Replies addReply(Replies replies);
    public int deleteReplyByReplyId(Integer id);
    public void deleteReplyList(List<Replies> repliesList);
}
