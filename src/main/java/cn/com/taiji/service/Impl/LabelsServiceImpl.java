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

    //通过LabelId查询标签信息以及所有讨论组
    @Transactional()
    @Override
    public Labels findLabelById(Integer id) {
        Labels label=labelsRepository.findOne(id);
        label.getGroupsList();
        return label;
    }

    //删除标签 涉及到级联删除，主从关系
    @Transactional()
    @Override
    public int deleteLabelByLabelId(Integer id) {
        int i = labelsRepository.deleteByLabelId(id);
        return i;
    }

    //新增标签
    @Transactional()
    @Override
    public Labels addLabel(Labels label) {
        Labels save = labelsRepository.save(label);
        return save;
    }
}
