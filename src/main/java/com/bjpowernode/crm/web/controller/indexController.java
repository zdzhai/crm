package com.bjpowernode.crm.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Zhai Zhidong
 * @version 1.0
 * @Date 2022/11/4 21:57
 */
@Controller
public class indexController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }
}
