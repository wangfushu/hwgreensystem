package com.xmrbi.hwgreensystem.controller;

import com.xmrbi.hwgreensystem.common.MessageConfigConstant;
import com.xmrbi.hwgreensystem.domain.db.SysPlaza;
import com.xmrbi.hwgreensystem.domain.db.Users;
import com.xmrbi.hwgreensystem.service.SysPlazaService;
import com.xmrbi.hwgreensystem.service.UsersService;
import com.xmrbi.hwgreensystem.until.WholeResponse;
import com.xmrbi.hwgreensystem.until.treeUtils.Tree;
import com.xmrbi.hwgreensystem.until.treeUtils.TreeListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@RequestMapping(value="/sysplaza")
@Scope("prototype")
public class PlazaController extends BaseControl {
    private static Logger logger = LoggerFactory.getLogger(PlazaController.class);

    @Autowired
    SysPlazaService sysPlazaService;
    @Autowired
    UsersService usersService;

    @RequestMapping(value="/index")
    public String index(HttpServletRequest request, HttpServletResponse response){
        logger.info("index跳转网点管理页面");
        return "/plazas/plaza";
    }
    @RequestMapping(value="/tree")
    @ResponseBody
    public Tree<SysPlaza> listTree(){
        try {
            Tree<SysPlaza> tree = new Tree<SysPlaza>();
            tree = sysPlazaService.getTree();
            return tree;
        } catch (Exception e) {
            logger.info("tree查询出错"+e.getMessage());
            return null;
        }
    }
    @RequestMapping(value="listSysPlaza")
    @ResponseBody
    public List<SysPlaza> listSysPlaza(HttpServletRequest request, HttpServletResponse response){
        try {
            List<SysPlaza> sysDeptList = sysPlazaService.list();
            return sysDeptList;
        } catch (Exception e) {
            logger.info("listSysPlaza查询出错"+e.getMessage());
            return null;
        }
    }

    @RequestMapping(value="listSysPlazalevel3")
    @ResponseBody
    public List<SysPlaza> listSysPlazalevel3(HttpServletRequest request, HttpServletResponse response){
        try {
            Users users=getCurrentUser();
            List<SysPlaza> sysPlazaList = sysPlazaService.findLevel3(users.getSysPlaza().getPlazaId(),3l);
            return sysPlazaList;
        } catch (Exception e) {
            logger.info("listSysPlaza查询出错"+e.getMessage());
            return null;
        }
    }


    @RequestMapping(value="/SysPlazaById/{plazaId}")
    @ResponseBody
    public SysPlaza SysPlazaById(@PathVariable("plazaId") Long plazaId, HttpServletRequest request, HttpServletResponse response){
        try {
            SysPlaza sysDeptList = sysPlazaService.get(plazaId);
            return sysDeptList;
        } catch (Exception e) {
            logger.info("listSysPlaza查询出错"+e.getMessage());
            return null;
        }
    }
    @RequestMapping(value="/addSysPlaza/{plazaId}")
    public String addSysPlaza(Model model, @PathVariable("plazaId") Long plazaId, HttpServletRequest request, HttpServletResponse response){
        logger.info("addSysPlaza跳转网点添加页面");
        try {
            SysPlaza sysPlaza= sysPlazaService.get(plazaId);
            model.addAttribute("pId",plazaId);
            model.addAttribute("pName",sysPlaza.getPlazaName());
            model.addAttribute("level",sysPlaza.getLevel()+1);
        } catch (Exception e) {
            logger.error("addSysPlaza跳转网点添加页面出错了"+e.getMessage());
        }
        return "/plazas/addPlaza";
    }

    @RequestMapping(value="savePlaza")
    @ResponseBody
    public WholeResponse savePlaza(SysPlaza sysPlaza, HttpServletRequest request, HttpServletResponse response){
        try {
            HttpSession session = request.getSession();
            if(null==sysPlaza.getDelFlag()){
                sysPlaza.setDelFlag(1);
            }
            SysPlaza sysPlaza1 = sysPlazaService.save(sysPlaza);
            if(null!=sysPlaza1){
                return WholeResponse.successResponse("新加网点成功");
            }else{
                return WholeResponse.errorResponse("1", "新加网点失败");
            }
        } catch (Exception e) {
            logger.error("savePlaza保存出错"+e.getMessage());
            return WholeResponse.errorResponse("500", "系统异常");
        }
    }


    /**
     * 树形展示页面
     * @return
     */
    @RequestMapping(value="/treeView")
    String treeView() {
        return  "/plazas/plazaTree";
    }

    /**
     * 自由添加网点
     * @return
     */
    @RequestMapping(value="/freeAddSysPlaza",method = RequestMethod.GET)
    String freeAddSysPlaza() {
        return  "/plazas/freeaddPlaza";
    }

    /**
     * 部门编辑页面
     * @param request
     * @param response
     * @author 福淑
     * @return
     */
    @RequestMapping(value="/editSysPlaza/{id}",method=RequestMethod.GET)
    public String editDept(@PathVariable("id") Long id,Model model,HttpServletRequest request,HttpServletResponse response){
        logger.info("editSysPlaza网点编辑 id参数为:"+id);
        try {
            SysPlaza sysPlaza = sysPlazaService.get(id);
            if(MessageConfigConstant.DEPT_ROOT_ID.equals(sysPlaza.getParentId())) {
                model.addAttribute("parentSysPlazaName",MessageConfigConstant.DEPT_ROOT_NAME);
            }else {
                SysPlaza parDept = sysPlazaService.get(sysPlaza.getParentId());
                model.addAttribute("parentSysPlazaName", parDept.getPlazaName());
            }
            model.addAttribute("sysPlaza", sysPlaza);
        } catch (Exception e) {
            logger.error("editSysPlaza出错"+e.getMessage());
            model.addAttribute("msg", "系统错误，请联系管理员");
        }
        return "/plazas/editSysPlaza";
    }
    @RequestMapping(value="/updateSysPlaza")
    @ResponseBody
    public WholeResponse updateSysPlaza(SysPlaza sysPlaza,HttpServletRequest request){
        logger.info("updateSysPlaza网点更新");
        try {
            HttpSession session = request.getSession();
            String flag= sysPlazaService.update(sysPlaza);
            if(flag.equals("success")){
                return WholeResponse.successResponse("更新网点成功");
            }else{
                return WholeResponse.errorResponse("1", "更新网点失败");
            }
            /*if(sysPlazaService.update(sysPlaza)>0){

            }else{
                return WholeResponse.errorResponse("1", "更新部门失败");
            }*/
        } catch (Exception e) {
            logger.error("updateSysPlaza网点更新出错"+e.getMessage());
            return WholeResponse.errorResponse("500", "系统异常");
        }
    }


    @RequestMapping(value = "/removeSysPlaza")
    @ResponseBody
    public WholeResponse removeSysPlaza(Long plazaId, HttpServletRequest request) {
        logger.info("removeUser用户删除");
        try {
            Users currentUser = getCurrentUser();
            SysPlaza sysPlaza = sysPlazaService.get(plazaId);
            List<Users> usersList=usersService.findByPlazaId(plazaId);
            List<Long> sysPlazaIds=sysPlazaService.findPlazaIdByParentId(plazaId);
            if (usersList.size()>0||sysPlazaIds.size()>0) {
                return WholeResponse.errorResponse("1", "该网点下还有用户或网点,请先删除网点下的数据");
            }
            sysPlazaService.delete(currentUser, sysPlaza);
            return WholeResponse.successResponse("删除数据成功");

        } catch (Exception e) {
            logger.error("removeSysPlaza网点删除出错" + e.getMessage());
            return WholeResponse.errorResponse("500", "系统异常");
        }
    }


}
