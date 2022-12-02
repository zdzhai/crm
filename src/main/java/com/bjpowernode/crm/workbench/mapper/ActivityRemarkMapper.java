package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.bean.ActivityRemark;

import java.util.List;

public interface ActivityRemarkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Fri Nov 18 21:16:08 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Fri Nov 18 21:16:08 CST 2022
     */
    int insert(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Fri Nov 18 21:16:08 CST 2022
     */
    int insertSelective(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Fri Nov 18 21:16:08 CST 2022
     */
    ActivityRemark selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Fri Nov 18 21:16:08 CST 2022
     */
    int updateByPrimaryKeySelective(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Fri Nov 18 21:16:08 CST 2022
     */
    int updateByPrimaryKey(ActivityRemark record);

    //查询市场活动评论
    List<ActivityRemark> selectActivityRemarkForDetailById(String id);

    //添加市场活动备注
    int insertActivityRemark(ActivityRemark activityRemark);

    //删除市场活动备注
    int deleteActivityRemarkById(String id);
}