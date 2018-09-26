package com.xmrbi.hwgreensystem.service;

import com.google.common.collect.Lists;
import com.xmrbi.hwgreensystem.dao.SysPlazaDao;
import com.xmrbi.hwgreensystem.dao.util.DynamicSpecifications;
import com.xmrbi.hwgreensystem.dao.util.SearchFilter;
import com.xmrbi.hwgreensystem.domain.db.SysPlaza;
import com.xmrbi.hwgreensystem.until.treeUtils.BuildTree;
import com.xmrbi.hwgreensystem.until.treeUtils.Tree;
import freemarker.template.utility.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
 * @description :
 * ---------------------------------
 * @Author: wangfushu
 * @Date: 2018-09-10 11:53
 */
@Service
public class SysPlazaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SysPlazaService.class);
    @Resource
    private SysPlazaDao sysPlazaDao;

    public Tree<SysPlaza> getTree() {
        List<Tree<SysPlaza>> trees = new ArrayList<Tree<SysPlaza>>();
        List<SysPlaza> sysPlazas = list();
        for (SysPlaza sysPlaza : sysPlazas) {
            Tree<SysPlaza> tree = new Tree<SysPlaza>();
            tree.setId(sysPlaza.getPlazaId().toString());
            tree.setParentId(sysPlaza.getParentId().toString());
            tree.setText(sysPlaza.getPlazaName());
            Map<String, Object> state = new HashMap<String, Object>();
            state.put("opened", true);
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<SysPlaza> t = BuildTree.build(trees);
        return t;
    }
    public List<SysPlaza> list(){
        List<SearchFilter> filters = Lists.newArrayList();
        Specification<SysPlaza> spec = DynamicSpecifications.bySearchFilter(filters, SysPlaza.class);
        List<SysPlaza> sysPlazas= sysPlazaDao.findAll(spec);
        return sysPlazas;
    }
    public SysPlaza get(Long plazaId){
        return sysPlazaDao.findByPlazaId(plazaId);
    }
    public SysPlaza save(SysPlaza sysPlaza){
        return sysPlazaDao.save(sysPlaza);
    }
    public String update(SysPlaza sysPlaza){
        SysPlaza dbsysPlaza=sysPlazaDao.findByPlazaId(sysPlaza.getPlazaId());
        if(null!=dbsysPlaza){
            if(null!=sysPlaza.getParentId())
                dbsysPlaza.setParentId(sysPlaza.getParentId());
            if (!StringUtils.isEmpty(sysPlaza.getPlazaName()))
                dbsysPlaza.setPlazaName(sysPlaza.getPlazaName());
            if (null!= sysPlaza.getDelFlag())
                dbsysPlaza.setDelFlag(sysPlaza.getDelFlag());
            if(null!= sysPlaza.getOrderNum())
                dbsysPlaza.setOrderNum(sysPlaza.getOrderNum());
            if (!StringUtils.isEmpty(sysPlaza.getPlazaRemark()))
                dbsysPlaza.setPlazaRemark(sysPlaza.getPlazaRemark());
           sysPlazaDao.save(dbsysPlaza);
           return "success";
        }else {
            return "error";
        }

    }
}