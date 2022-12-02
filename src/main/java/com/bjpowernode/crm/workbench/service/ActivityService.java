package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.bean.Activity;
import com.bjpowernode.crm.workbench.bean.ClueRemark;
import com.sun.corba.se.spi.orbutil.fsm.ActionBase;

import java.util.List;
import java.util.Map;

/**
 * @author Zhai Zhidong
 * @version 1.0
 * @Date 2022/11/6 20:35
 */
public interface ActivityService {
    int saveCreatActivity(Activity activity);

    List<Activity> queryActivityByConditionForPage(Map<String,Object> map);

    int queryCountOfActivityByCondition(Map<String,Object> map);

    int deleteActivityByIds(String[] ids);

    Activity queryActivityById(String id);

    int saveEditActivityById(Activity activity);

    List<Activity> queryAllActivity();

    int saveCreateActivityByList(List<Activity> activityList);

    Activity queryActivityForDetailById(String id);

    List<Activity> queryActivityForDetailByClueId(String id);

    List<Activity> queryActivityForDetailByNameClueId(Map<String,Object> map);

    List<Activity> queryActivityForDetailByIds(String[] ids);
}
