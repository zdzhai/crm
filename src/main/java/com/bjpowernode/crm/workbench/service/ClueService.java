package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.bean.Clue;

import java.lang.ref.SoftReference;

/**
 * @author Zhai Zhidong
 * @version 1.0
 * @Date 2022/11/21 22:00
 */
public interface ClueService {

    int saveClue(Clue clue);

    Clue queryClueForDetailById(String id);
}
