package com.bjpowernode.crm.commons.utils;

import java.util.UUID;

/**
 * @author Zhai Zhidong
 * @version 1.0
 * @Date 2022/11/7 8:54
 */
public class UUIDUtils {
    /**
     * 获取UUID的值
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");

    }
}
