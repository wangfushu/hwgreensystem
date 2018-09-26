package com.xmrbi.hwgreensystem.controller;

import com.xmrbi.hwgreensystem.domain.db.Users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wangfs on 2018-04-02 helloword.
 */
@RequestMapping("/home")
@Controller
public class HomeControl extends BaseControl{
    @RequestMapping(value = "")
    public String home(Model model) {
        Users currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/login";
        }
        return "index";
        //return  null;
    }
}
