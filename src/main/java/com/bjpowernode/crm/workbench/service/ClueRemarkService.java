package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.bean.ClueRemark;

import java.util.List;

/**
 * @author Zhai Zhidong
 * @version 1.0
 * @Date 2022/11/28 20:54
 */
public interface ClueRemarkService {
    List<ClueRemark> queryCLueRemarkForDetailByClueId(String id);
}
