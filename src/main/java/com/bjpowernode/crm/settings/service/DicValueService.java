package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.bean.DicValue;

import java.util.List;

/**
 * @author Zhai Zhidong
 * @version 1.0
 * @Date 2022/11/21 22:19
 */
public interface DicValueService {
    List<DicValue> queryDicValueByTypeCode(String typeCode);
}
