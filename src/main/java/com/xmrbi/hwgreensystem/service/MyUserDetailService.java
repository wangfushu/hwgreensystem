package com.xmrbi.hwgreensystem.service;


import com.xmrbi.hwgreensystem.domain.db.Role;
import com.xmrbi.hwgreensystem.domain.db.Users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Administrator on 2016/2/21.
 */
@Component
public class MyUserDetailService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UsersService usersService;

    @Transactional(rollbackFor = Exception.class)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.trim().equals("")) {
            throw new UsernameNotFoundException("登录名不可为空！");
        }
        Users dbUser = usersService.findByName(username);
        if (dbUser == null) {
            logger.info("{} user not found in db", username);
            throw new UsernameNotFoundException("用户" + username + " 不存在");
        }
        Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        for (Role role : dbUser.getRoles()) {
            SimpleGrantedAuthority simpleGrantedAuthority1 = new SimpleGrantedAuthority(role.getName());
            auths.add(simpleGrantedAuthority1);
        }
        User user = new User(dbUser.getUserNo(), dbUser.getPassword(), auths);
        return user;
    }
}
