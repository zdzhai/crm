package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.bean.ReturnObject;
import com.bjpowernode.crm.commons.constants.Constants;
import com.bjpowernode.crm.commons.utils.FormatDateTime;
import com.bjpowernode.crm.settings.bean.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zhai Zhidong
 * @version 1.0
 * @Date 2022/11/4 22:13
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/settings/qx/user/toLogin.do")
    //请求转发到登录页面
    public String toLogin(){
        return "settings/qx/user/login";
    }

    @RequestMapping("/settings/qx/user/login.do")
    public @ResponseBody Object login(String loginAct, String loginPwd,String isRemPwd, HttpServletRequest request,HttpServletResponse response,HttpSession session){
        Map<String, Object> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        map.put("isRemPwd",isRemPwd);
        //调用Service层处理请求数据
        User user = userService.queryUserByLoginActAndLoginPwd(map);
        ReturnObject returnObject = new ReturnObject();
        //根据查询结果，生成响应信息
        if (user == null){
            //登陆失败
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("账号或密码错误");
        } else {
            String now = FormatDateTime.formatDateTime(new Date());
            if (now.compareTo(user.getExpireTime())>0){
                //账号已过期
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("账号已过期");
            } else if (user.getLockState().equals("0")){
                //账号被锁定
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("账号被锁定");
            } else if(!user.getAllowIps().contains(request.getRemoteAddr())){
                //ip受限
                System.out.println(request.getRemoteAddr()+"正在尝试访问");
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("ip访问受限");
            } else {
                //登录成功
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);

                //在Session域中共享user数据用于在其他页面使用user数据
                session.setAttribute(Constants.SESSION_OBJECT,user);

                if ("true".equals(isRemPwd)){
                    //将账号和密码保存到Cookie
                    Cookie cookieAct = new Cookie("loginAct", user.getLoginAct());
                    cookieAct.setMaxAge(10*24*60*60);
                    response.addCookie(cookieAct);
                    Cookie cookiePwd = new Cookie("loginPwd", loginPwd);
                    cookiePwd.setMaxAge(10*24*60*60);
                    response.addCookie(cookiePwd);
                } else {
                    Cookie cookieAct = new Cookie("loginAct","1");
                    cookieAct.setMaxAge(0);
                    response.addCookie(cookieAct);
                    Cookie cookiePwd = new Cookie("loginPwd", "1");
                    cookiePwd.setMaxAge(0);
                    response.addCookie(cookiePwd);
                }

            }
        }
        return returnObject;
    }

    @RequestMapping("/settings/qx/user/logout.do")
    public String logout(HttpServletResponse response,HttpSession session){
        Cookie cookieAct = new Cookie("loginAct","1");
        cookieAct.setMaxAge(0);
        response.addCookie(cookieAct);
        Cookie cookiePwd = new Cookie("loginPwd", "1");
        cookiePwd.setMaxAge(0);
        response.addCookie(cookiePwd);

        session.invalidate();
        return "redirect:/";
    }
}
