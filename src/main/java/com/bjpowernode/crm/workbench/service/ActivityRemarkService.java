package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.bean.ActivityRemark;

import java.util.List;

/**
 * @author Zhai Zhidong
 * @version 1.0
 * @Date 2022/11/18 21:43
 */
public interface ActivityRemarkService {
    List<ActivityRemark> selectActivityDetailByActivityId(String id);

    int saveActivityRemark(ActivityRemark activityRemark);

    int deleteActivityRemarkById(String id);
}
