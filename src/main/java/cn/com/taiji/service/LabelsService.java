package cn.com.taiji.service;

import cn.com.taiji.domain.Labels;

import java.util.List;

/**
 * @ Author     ：liutong.
 * @ Date       ：Created in 20:46 2018/12/17
 * @ Description：${description}
 * @ Modified By：
 * @ Version:     $version
 */
public interface LabelsService {

    public List<Labels> findAllLabels();

    public Labels findLabelById(Integer id);
}
