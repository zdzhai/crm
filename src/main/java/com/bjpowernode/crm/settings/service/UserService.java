package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.bean.User;

import java.util.List;
import java.util.Map;

/**
 * @author Zhai Zhidong
 * @version 1.0
 * @Date 2022/11/5 11:50
 */
public interface UserService {
    public User queryUserByLoginActAndLoginPwd(Map<String,Object> map);

    public List<User> queryAllUsers();
}
