package com.xmrbi.hwgreensystem.controller;


import com.alibaba.fastjson.JSONObject;
import com.xmrbi.hwgreensystem.domain.db.Users;
import com.xmrbi.hwgreensystem.service.UsersService;
import com.xmrbi.hwgreensystem.until.LoginResponse;
import com.xmrbi.hwgreensystem.until.PrintUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * code is far away from bug with the animal protecting
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @description :
 * ---------------------------------
 * @Author: wangfushu
 * @Date: 2018-09-06 9:50
 */
@RequestMapping("/login")
@Controller
public class LoginControl {
    private static Logger logger = LoggerFactory.getLogger(LoginControl.class);
    @Autowired
    UsersService usersService;

    @RequestMapping(value = "")
    public String login(String msg, Model model) {
        System.out.println("msg=" + msg);
        model.addAttribute("msg", msg);
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String applogin(String userName, String password, HttpServletRequest request, HttpServletResponse response) {
        logger.info("login被访问，请求参数为:username=" + userName + "\tpassword=" + password);

        LoginResponse loginResponse = null;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        try {
            if (userName.equals("") || password.equals("")) {
                loginResponse = LoginResponse.errorLogin("用户名或密码不能为空");
                PrintUtil.printJson(response, JSONObject.toJSONString(loginResponse));
            } else {
                //UserDO userDO = userService.retrieve(username, password);
                Users users = usersService.findByName(userName);
                if (null != users&&users.getStatus()!=0) {
                    if (passwordEncoder.matches(password, users.getPassword())) {
                        if (users.getIsInspector()) {
                            loginResponse = LoginResponse.successLogin();
                            loginResponse.setMsg(JSONObject.toJSONString(users));
                            PrintUtil.printJson(response, JSONObject.toJSONString(loginResponse));
                        }else{
                            logger.info("login被访问，请求参数为:username=" + userName + "\tpassword=" + password +" 无稽查员权限");
                            loginResponse = LoginResponse.errorLogin("无稽查员权限");
                            PrintUtil.printJson(response, JSONObject.toJSONString(loginResponse));
                        }
                    }else{
                        logger.info("login被访问，请求参数为:username=" + userName + "\tpassword=" + password +" 用户名密码错误");
                        loginResponse = LoginResponse.errorLogin("用户名或密码错误");
                        PrintUtil.printJson(response, JSONObject.toJSONString(loginResponse));
                    }
                }else{
                    logger.info("login被访问，请求参数为:username=" + userName + "\tpassword=" + password +" 无此用户");
                    loginResponse = LoginResponse.errorLogin("无此用户");
                    PrintUtil.printJson(response, JSONObject.toJSONString(loginResponse));
                }

            }
        } catch (Exception e) {
            logger.error("出错了" + e.getMessage());
            loginResponse = LoginResponse.errorLogin("系统错误,稍后再试");
            PrintUtil.printJson(response, JSONObject.toJSONString(loginResponse));
        }
        return null;
    }


}
