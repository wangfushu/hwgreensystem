package com.xmrbi.hwgreensystem.controller;


import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.xmrbi.hwgreensystem.domain.db.Role;
import com.xmrbi.hwgreensystem.domain.db.SysPlaza;
import com.xmrbi.hwgreensystem.domain.db.Users;
import com.xmrbi.hwgreensystem.domain.param.UserParam;
import com.xmrbi.hwgreensystem.service.SysPlazaService;
import com.xmrbi.hwgreensystem.service.UsersService;
import com.xmrbi.hwgreensystem.until.PageUtils;
import com.xmrbi.hwgreensystem.until.StringUtil;
import com.xmrbi.hwgreensystem.until.WholeResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * 用户管理Controller
 *
 * @author 福淑
 */
@Controller
@RequestMapping(value = "/user")
@Scope("prototype")
public class UserController extends BaseControl {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UsersService userService;
    @Autowired
    SysPlazaService sysPlazaService;

    /*	@Autowired
        RoleService roleService;*/
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, HttpServletResponse response) {
        logger.info("index跳转用户管理页面");
        return "/users/user";
    }

    @RequestMapping(value = "/listUser")
    @ResponseBody
    public PageUtils listUser(UserParam userParam, HttpServletRequest request, HttpServletResponse response) {
        try {
            //查询列表数据 对分页参数进行类型转换
            Page<Users> userDOs = userService.pageUsers(userParam);
            PageUtils pageUtils = new PageUtils(userDOs.getContent(), Integer.valueOf(String.valueOf(userDOs.getTotalElements())));
            return pageUtils;
        } catch (Exception e) {
            logger.info("listUser查询出错" + e.getMessage());
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "usernameNotExist")
    public String usernameNotExist(String userNo) {
        Preconditions.checkNotNull(userNo, "username 不能为空");
        Users users = userService.findByName(userNo);
        return users == null ? "true" : "false";
    }

    @RequestMapping(value = "/addUser")
    public String addUser(Model model) {
        List<Role> roles = userService.listAllRole();
        model.addAttribute("roles", roles);
        return "/users/addUser";
    }

    @ResponseBody
    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public WholeResponse add(Users users, Long roleId) throws UnsupportedEncodingException {
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (users.getId() != null) {
                Users oldUser = userService.findById(users.getId());
                oldUser.setUserNo(users.getUserNo());
                oldUser.setUserName(users.getUserName());
                if (oldUser.getSysPlaza().getPlazaId() != users.getSysPlaza().getPlazaId()) {
                    SysPlaza sysPlaza = sysPlazaService.get(users.getSysPlaza().getPlazaId());
                    oldUser.setSysPlaza(sysPlaza);
                }
                if (!StringUtil.isEmpty(users.getPassword()))
                    oldUser.setPassword(passwordEncoder.encode(users.getPassword()));
                if (!StringUtil.isEmpty(users.getTelphone()))
                    oldUser.setTelphone(users.getTelphone());
                if (!StringUtil.isEmpty(users.getFax()))
                    oldUser.setFax(users.getFax());
                if (!StringUtil.isEmpty(users.getEmail()))
                    oldUser.setEmail(users.getEmail());
                if (!StringUtil.isEmpty(users.getiDCard()))
                    oldUser.setiDCard(users.getiDCard());
                if (!StringUtil.isEmpty(users.getAddress()))
                    oldUser.setAddress(users.getAddress());
                if (!StringUtil.isEmpty(users.getZip()))
                    oldUser.setZip(users.getZip());
                //oldUser.setEmail(users.getEmail());
                if (!StringUtil.isEmpty(users.getRemark()))
                    oldUser.setRemark(users.getRemark());
                oldUser.setGmtModify(new Date());
                if (null != roleId) {
                    Role role = userService.findByRoleId(roleId);
                    oldUser.setRoles(Lists.newArrayList(role));
                }
                userService.saveOrUpdate(oldUser);
                return WholeResponse.successResponse("更新数据成功");
            } else {
                if (!StringUtil.isEmpty(users.getPassword())) {
                    users.setPassword(passwordEncoder.encode(users.getPassword()));
                }
                if (null != roleId) {
                    Role role = userService.findByRoleId(roleId);
                    users.setRoles(Lists.newArrayList(role));
                }
                users.setGmtCreate(new Date());
                Users save = userService.saveOrUpdate(users);
                return WholeResponse.successResponse("保存数据成功");
            }
        } catch (Exception e) {
            logger.error("saveUser保存出错" + e.getMessage());
            return WholeResponse.errorResponse("500", "系统异常");
        }
    }

    @RequestMapping(value = "/removeUser")
    @ResponseBody
    public WholeResponse removeUser(Long id, HttpServletRequest request) {
        logger.info("removeUser用户删除");
        try {
            Users currentUser = getCurrentUser();
            Users users = userService.findById(id);
            if (currentUser.getId().equals(id)) {
                return WholeResponse.errorResponse("1", "该用户是本人，不可删除");
            }
            userService.delete(currentUser, users);
            return WholeResponse.successResponse("删除数据成功");

        } catch (Exception e) {
            logger.error("removeUser用户删除出错" + e.getMessage());
            return WholeResponse.errorResponse("500", "系统异常");
        }
    }

    @RequestMapping(value = "/batchRemoveUser")
    @ResponseBody
    public WholeResponse batchRemoveUser(@RequestParam("ids[]") Long[] ids, HttpServletRequest request) {
        logger.info("batchRemoveUser用户批量删除");
        try {
            Users currentUser = getCurrentUser();
            List<Users> usersList = Lists.newArrayList();
            for (Long id : ids) {
                Users users = userService.findById(id);
                if (!currentUser.getId().equals(users.getId())) {
                    usersList.add(users);
                    // "该用户是本人，不可删除");
                }
            }
            userService.delete(currentUser, usersList);
            return WholeResponse.successResponse("批量删除数据成功");
            //return WholeResponse.errorResponse("1", "批量删除失败");

        } catch (Exception e) {
            logger.error("batchRemoveUser用户批量删除出错" + e.getMessage());
            return WholeResponse.errorResponse("500", "系统异常");
        }
    }

    @RequestMapping(value = "/editUser/{id}")
    public String editUser(Model model, @PathVariable("id") Long id) {
        try {
            Users userDO = userService.findById(id);
            model.addAttribute("user", userDO);
            List<Role> roles = userService.listAllRole();
            model.addAttribute("roles", roles);
            long roleSign = 0;
            for (Role role : userDO.getRoles()) {
                roleSign = role.getId();
            }
            model.addAttribute("roleSign", roleSign);
        } catch (Exception e) {
            logger.error("editUser出错" + e.getMessage());
            model.addAttribute("msg", "系统错误，请联系管理员");
        }
        return "/users/editUser";
    }

    /**
     * 跳转重置密码页面
     *
     * @param userId 用户id
     * @param model  对象数据
     * @return
     */
    @RequestMapping(value = "/resetPwd/{id}", method = RequestMethod.GET)
    public String resetPwd(@PathVariable("id") Long userId, Model model) {
        try {
            Users userDO = new Users();
            userDO.setId(userId);
            model.addAttribute("user", userDO);
        } catch (Exception e) {
            logger.error("跳转重置密码页面出错了" + e.getMessage());
        }
        return "/users/reset_pwd";
    }


    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    @ResponseBody
    public WholeResponse restPwd(Users users, HttpServletRequest request) {
        logger.info("restPwd重置密码");
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Users dbuser = userService.findById(users.getId());
            dbuser.setPassword(passwordEncoder.encode(users.getPassword()));
            userService.saveOrUpdate(dbuser);

            return WholeResponse.successResponse("重置密码成功");
            //return WholeResponse.errorResponse("1", "重置密码失败");
        } catch (Exception e) {
            logger.error("restPwd重置密码出错" + e.getMessage());
            return WholeResponse.errorResponse("500", "系统异常");
        }
    }


    @ResponseBody
    @RequestMapping(value = "checkPassword")
    public String checkPassword(String oldPassword) {
        Preconditions.checkNotNull(oldPassword, "oldPassword 不能为空");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Users currentUser = getCurrentUser();
        if (passwordEncoder.matches(oldPassword,currentUser.getPassword())) {
            return "true";
        }else{
            return "false";
        }
    }

    @ResponseBody
    @RequestMapping(value = "changePassword")
    public String changePassword(String oldPassword, String newpassword) {
        Preconditions.checkNotNull(newpassword, "password 不能为空");
        Users currentUser = getCurrentUser();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(oldPassword,currentUser.getPassword())) {
            currentUser.setPassword( passwordEncoder.encode(newpassword));
            currentUser.setGmtModify(new Date());
            userService.saveOrUpdate(currentUser);
            return "ok";
        }
        return "fail";
    }
}
