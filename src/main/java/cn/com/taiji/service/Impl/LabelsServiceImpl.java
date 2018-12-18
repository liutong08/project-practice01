package cn.com.taiji.service.Impl;

import cn.com.taiji.domain.Labels;
import cn.com.taiji.repository.LabelsRepository;
import cn.com.taiji.service.LabelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ Author     ：liutong.
 * @ Date       ：Created in 20:46 2018/12/17
 * @ Description：${description}
 * @ Modified By：
 * @ Version:     $version
 */
@Service
public class LabelsServiceImpl implements LabelsService {
    @Autowired
    private LabelsRepository labelsRepository;

    //查询所有标签
    @Transactional()
    @Override
    public List<Labels> findAllLabels() {
        List<Labels> labelsList=labelsRepository.findAll();
        return labelsList;
    }
    //查询标签下的所有讨论组
    @Transactional()
    @Override
    public Labels findLabelById(Integer id) {
        Labels label=labelsRepository.findOne(id);
        label.getGroupsList();
        return label;
    }
}
