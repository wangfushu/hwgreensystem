package com.xmrbi.hwgreensystem.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yangjb on 2017/7/28.
 * helloWorld
 */
@RequestMapping("/accessDeniedPage")
@Controller
public class AccessDenyControl {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessDenyControl.class);

    @RequestMapping(value = "")
    public String deny(Model model) {
        return "deny";
    }

    @RequestMapping(value = "error500")
    public String error500(HttpServletRequest request) {
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        throwable.printStackTrace();
        return "error500";
    }
}
