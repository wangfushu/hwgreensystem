package com.xmrbi.hwgreensystem.interceptor;


import com.google.common.collect.Lists;
import com.xmrbi.hwgreensystem.domain.db.Role;
import com.xmrbi.hwgreensystem.domain.db.Users;
import com.xmrbi.hwgreensystem.interceptor.annotation.UnInterception;
import com.xmrbi.hwgreensystem.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;


public class CurrentUserInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger = LoggerFactory.getLogger(CurrentUserInterceptor.class);
    @Resource
    private UsersService usersService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        getCurrentUser(request);
        return super.preHandle(request, response, handler);
    }

    private void getCurrentUser(HttpServletRequest request) throws ServletException, IOException {
        Users users = (Users) request.getSession().getAttribute("currentUser");
        //Users users = usersService.getById(1);
        if (users == null || users.getId() == null) {
            if (SecurityContextHolder.getContext() != null &&
                    SecurityContextHolder.getContext().getAuthentication() != null &&
                    SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null &&
                    SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                        .getAuthentication().getPrincipal();
                if (userDetails != null) {
                    String username = userDetails.getUsername();
                    users = usersService.findByName(username);
                    if (!CollectionUtils.isEmpty(users.getRoles())) {
                        List<String> roleStr = Lists.newArrayList();
                        for (Role role : users.getRoles()) {
                            roleStr.add(role.getName());
                        }
                        request.getSession().setAttribute("currentRole", roleStr);
                    }
                    request.getSession().setAttribute("currentUser", users);
                }
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
