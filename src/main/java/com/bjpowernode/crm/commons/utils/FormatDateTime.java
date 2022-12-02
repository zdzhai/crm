package com.bjpowernode.crm.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Zhai Zhidong
 * @version 1.0
 * @Date 2022/11/5 15:17
 */
public class FormatDateTime {
    public static String formatDateTime(Date date){
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    }
}
