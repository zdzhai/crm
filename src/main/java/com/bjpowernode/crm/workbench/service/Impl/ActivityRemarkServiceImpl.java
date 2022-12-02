package com.bjpowernode.crm.workbench.service.Impl;

import com.bjpowernode.crm.workbench.bean.ActivityRemark;
import com.bjpowernode.crm.workbench.mapper.ActivityRemarkMapper;
import com.bjpowernode.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zhai Zhidong
 * @version 1.0
 * @Date 2022/11/18 21:44
 */
@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {
    @Autowired
    ActivityRemarkMapper activityRemarkMapper;
    @Override
    public List<ActivityRemark> selectActivityDetailByActivityId(String id) {
        return activityRemarkMapper.selectActivityRemarkForDetailById(id);
    }

    @Override
    public int saveActivityRemark(com.bjpowernode.crm.workbench.bean.ActivityRemark activityRemark) {
        return activityRemarkMapper.insertActivityRemark(activityRemark);
    }

    @Override
    public int deleteActivityRemarkById(String id) {
        return activityRemarkMapper.deleteActivityRemarkById(id);
    }
}
