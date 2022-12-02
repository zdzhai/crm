package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.bean.ClueActivityRelation;

import java.util.List;

/**
 * @author Zhai Zhidong
 * @version 1.0
 * @Date 2022/11/29 22:10
 */
public interface ClueActivityRelationService {
    int saveCreateClueActivityRelationByList(List<ClueActivityRelation> list);
}
