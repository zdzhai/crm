package com.bjpowernode.crm.commons.bean;

/**
 * @author Zhai Zhidong
 * @version 1.0
 * @Date 2022/11/5 13:53
 */
public class ReturnObject {
    private String code;//成功或失败的标记 1 成功 0 成败
    private String message;//提示信息
    private Object retData;//其他信息

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRetData() {
        return retData;
    }

    public void setRetData(Object retData) {
        this.retData = retData;
    }
}
