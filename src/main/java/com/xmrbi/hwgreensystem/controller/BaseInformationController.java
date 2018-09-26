package com.xmrbi.hwgreensystem.controller;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.xmrbi.hwgreensystem.common.MessageConfigConstant;
import com.xmrbi.hwgreensystem.dao.SysBaseInformationDao;
import com.xmrbi.hwgreensystem.domain.db.SysBaseInformation;
import com.xmrbi.hwgreensystem.domain.db.SysPlaza;
import com.xmrbi.hwgreensystem.domain.db.Users;
import com.xmrbi.hwgreensystem.domain.param.BaseInformationParam;
import com.xmrbi.hwgreensystem.domain.param.UserParam;
import com.xmrbi.hwgreensystem.domain.vo.TreeVo;
import com.xmrbi.hwgreensystem.service.BaseInformationService;
import com.xmrbi.hwgreensystem.service.SysPlazaService;
import com.xmrbi.hwgreensystem.until.PageUtils;
import com.xmrbi.hwgreensystem.until.StringUtil;
import com.xmrbi.hwgreensystem.until.WholeResponse;
import com.xmrbi.hwgreensystem.until.treeUtils.Tree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

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
 *
 * @description :   网点管理
 * ---------------------------------
 * @Author: wangfushu
 * @Date: 2018-09-10 11:50
 */
@Controller
@RequestMapping(value="/baseinformation")
@Scope("prototype")
public class BaseInformationController extends BaseControl {
    private static Logger logger = LoggerFactory.getLogger(BaseInformationController.class);

    @Autowired
    SysPlazaService sysPlazaService;
    @Autowired
    BaseInformationService baseInformationService;

    @RequestMapping(value="/index")
    public String index(HttpServletRequest request, HttpServletResponse response){
        logger.info("index跳转基础信息管理页面");
        return "/baseinformation/baseinformation";
    }
    @RequestMapping(value="/tree")
    @ResponseBody
    public Tree<TreeVo> listTree(){
        try {
            Tree<TreeVo> tree = new Tree<TreeVo>();
            tree = baseInformationService.getTree();
            return tree;
        } catch (Exception e) {
            logger.info("baseinformationtree查询出错"+e.getMessage());
            return null;
        }
    }

    @RequestMapping(value="/vehicleTypeList")
    @ResponseBody
    public List<SysBaseInformation> vehicleTypeList(HttpServletRequest request, HttpServletResponse response){
        try {
            BaseInformationParam baseInformationParam = new BaseInformationParam();
            baseInformationParam.setType("freeType");
            baseInformationParam.setaMemo("1");
            List<SysBaseInformation> sysBaseInformations=baseInformationService.listSysBaseInformation(baseInformationParam);
            return sysBaseInformations;
        } catch (Exception e) {
            logger.info("vehicleTypeList查询出错"+e.getMessage());
            return null;
        }
    }

    @RequestMapping(value = "/listBaseInformation")
    @ResponseBody
    public PageUtils listBaseInformation(BaseInformationParam baseInformationParam, HttpServletRequest request, HttpServletResponse response) {
        try {
            //查询列表数据 对分页参数进行类型转换
            Page<SysBaseInformation> sysBaseInformations = baseInformationService.pageSysBaseInformation(baseInformationParam);
            PageUtils pageUtils = new PageUtils(sysBaseInformations.getContent(), Integer.valueOf(String.valueOf(sysBaseInformations.getTotalElements())));
            return pageUtils;
        } catch (Exception e) {
            logger.info("listBaseInformation查询出错" + e.getMessage());
            return null;
        }
    }

    @RequestMapping(value="/addSysBaseInformation/{id}")
    public String addSysBaseInformation(Model model,@PathVariable("id") Long id,HttpServletRequest request, HttpServletResponse response){
        logger.info("addSysBaseInformation跳转网点添加页面");
        try {
            SysBaseInformation sysBaseInformation = baseInformationService.findById(id);

            BaseInformationParam baseInformationParam = new BaseInformationParam();
            baseInformationParam.setTypeId(sysBaseInformation.getBiTypeId());
            List<SysBaseInformation> sysBaseInformations=baseInformationService.listSysBaseInformation(baseInformationParam);
            model.addAttribute("sysBaseInformations",sysBaseInformations);

        } catch (Exception e) {
            logger.error("addSysBaseInformation跳转网点添加页面出错了"+e.getMessage());
        }
        return "/baseinformation/addBaseInformation";
    }

    @RequestMapping(value="saveBaseInformation")
    @ResponseBody
    public WholeResponse saveBaseInformation(String entities, HttpServletRequest request, HttpServletResponse response){
        try {
            Users currentUser= getCurrentUser();
            List<SysBaseInformation> sysBaseInformations=(List<SysBaseInformation>)JSONArray.parseArray(entities, SysBaseInformation.class);

            for(int i=0;i<sysBaseInformations.size();i++){
                sysBaseInformations.get(i).setBiUserId(String.valueOf(currentUser.getId()));
                sysBaseInformations.get(i).setBiUserNo(currentUser.getUserNo());
                sysBaseInformations.get(i).setBiUserName(currentUser.getUserName());
                sysBaseInformations.get(i).setBiModifyTime(new Date());
            }
            baseInformationService.save(sysBaseInformations);
            return WholeResponse.successResponse("更新数据成功");
        } catch (Exception e) {
            logger.error("saveBaseInformation保存出错"+e.getMessage());
            return WholeResponse.errorResponse("500", "系统异常");
        }
    }
    @RequestMapping(value = "/remove")
    @ResponseBody
    public WholeResponse removeSysBaseInformation(Long id, HttpServletRequest request) {
        logger.info(" removeSysBaseInformation基础信息删除");
        try {
            Users currentUser = getCurrentUser();
            SysBaseInformation sysBaseInformation = baseInformationService.findById(id);
            baseInformationService.delete(currentUser, sysBaseInformation);
            return WholeResponse.successResponse("删除数据成功");

        } catch (Exception e) {
            logger.error("removeSysBaseInformation基础信息删除出错" + e.getMessage());
            return WholeResponse.errorResponse("500", "系统异常");
        }
    }

    @RequestMapping(value = "/batchRemove")
    @ResponseBody
    public WholeResponse batchRemoveSysBaseInformation(@RequestParam("ids[]") Long[] ids, HttpServletRequest request) {
        logger.info("batchRemoveSysBaseInformation基础信息批量删除");
        try {
            Users currentUser = getCurrentUser();
            List<SysBaseInformation> baseInformationArrayList = Lists.newArrayList();
            for (Long id : ids) {
                SysBaseInformation sysBaseInformation = baseInformationService.findById(id);
                    baseInformationArrayList.add(sysBaseInformation);
            }
            baseInformationService.delete(currentUser, baseInformationArrayList);
            return WholeResponse.successResponse("批量删除数据成功");
            //return WholeResponse.errorResponse("1", "批量删除失败");

        } catch (Exception e) {
            logger.error("batchRemoveSysBaseInformation基础信息批量删除出错" + e.getMessage());
            return WholeResponse.errorResponse("500", "系统异常");
        }
    }





}
