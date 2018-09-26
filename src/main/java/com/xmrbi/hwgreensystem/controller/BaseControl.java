package com.xmrbi.hwgreensystem.controller;


import com.xmrbi.hwgreensystem.domain.db.Users;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Validator;


public abstract class BaseControl {
    @Autowired
    protected Validator validator;
    @Autowired
    protected HttpSession session;
    @Autowired
    protected HttpServletRequest request;

    protected String getCurrentPath() {
        return (String) request.getAttribute("absoluteContextPath");
    }


   /* public Employee getCurrentUser() {
        return (Employee) session.getAttribute("currentUser");
    }
*/
   public Users getCurrentUser() {
       return (Users) session.getAttribute("currentUser");
   }

    private void add2CookieIfNotNull(HttpServletResponse httpServletResponse, String name, Object value) {
        if (value == null) {
            value = "";
        }
        Cookie cookie = new Cookie(name, value.toString());
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
    }
}
