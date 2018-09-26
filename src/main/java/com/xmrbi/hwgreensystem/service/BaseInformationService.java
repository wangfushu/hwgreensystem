package com.xmrbi.hwgreensystem.service;

import com.google.common.collect.Lists;
import com.xmrbi.hwgreensystem.controller.BaseControl;
import com.xmrbi.hwgreensystem.dao.SysBaseInformationDao;
import com.xmrbi.hwgreensystem.dao.SysPlazaDao;
import com.xmrbi.hwgreensystem.dao.util.DynamicSpecifications;
import com.xmrbi.hwgreensystem.dao.util.SearchFilter;
import com.xmrbi.hwgreensystem.domain.db.SysBaseInformation;
import com.xmrbi.hwgreensystem.domain.db.SysPlaza;
import com.xmrbi.hwgreensystem.domain.db.Users;
import com.xmrbi.hwgreensystem.domain.param.BaseInformationParam;
import com.xmrbi.hwgreensystem.domain.vo.TreeVo;
import com.xmrbi.hwgreensystem.until.StringUtil;
import com.xmrbi.hwgreensystem.until.treeUtils.BuildTree;
import com.xmrbi.hwgreensystem.until.treeUtils.Tree;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.util.*;

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
public class BaseInformationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseInformationService.class);
    @Resource
    private SysBaseInformationDao sysBaseInformationDao;

    @PersistenceUnit
    private EntityManagerFactory emf;

    public SysBaseInformation findById(Long id){
        Optional<SysBaseInformation> sysBaseInformation=sysBaseInformationDao.findById(id);
        return sysBaseInformation.get();
    }

    public List<SysBaseInformation> save(List<SysBaseInformation> sysBaseInformations){
        return Lists.newArrayList(sysBaseInformationDao.saveAll(sysBaseInformations));
    }

    public Tree<TreeVo> getTree() {
        List<Tree<TreeVo>> trees = new ArrayList<Tree<TreeVo>>();
        List<TreeVo> treeVos = list();
        for (TreeVo treeVo : treeVos) {
            Tree<TreeVo> tree = new Tree<TreeVo>();
            tree.setId(treeVo.getKid().toString());
            tree.setParentId("0");
            tree.setText(treeVo.getName());
            Map<String, Object> state = new HashMap<String, Object>();
            state.put("opened", true);
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<TreeVo> t = BuildTree.build(trees);
        return t;
    }

    public List<TreeVo> list(){
       // List<TreeVo> treeVos= sysBaseInformationDao.getAllType();
        EntityManager em = emf.createEntityManager();
        //定义SQL
        String sql = "select distinct Bi_Type as name,Bi_TypeId as kid  from sys_baseinformation ";
        Query query = em.createNativeQuery(sql);
        //执行查询，返回的是对象数组(Object[])列表,
        //每一个对象数组存的是相应的实体属性
        List<Object[]>  objectList =query.getResultList();
        List<TreeVo> treeVos= Lists.newArrayList();
        for(Object[] obj : objectList){
            TreeVo treeVo =new TreeVo();
            if(null!=obj[0])
                treeVo.setName(String.valueOf(obj[0]));
            if(null!=obj[1])
                treeVo.setKid(Long.valueOf(String.valueOf(obj[1])));
          treeVos.add(treeVo);
        }
        em.close();
        return treeVos;
    }

   public Page<SysBaseInformation> pageSysBaseInformation(BaseInformationParam baseInformationParam){
       List<SearchFilter> filters = Lists.newArrayList();
       if(!StringUtil.isEmpty(baseInformationParam.getTypeId()))
           filters.add(new SearchFilter("biTypeId", SearchFilter.Operator.EQ, baseInformationParam.getTypeId()));
       if(!StringUtil.isEmpty(baseInformationParam.getType()))
           filters.add(new SearchFilter("biType", SearchFilter.Operator.LIKE, baseInformationParam.getType()));
       Specification<SysBaseInformation> spec = DynamicSpecifications.bySearchFilter(filters, SysBaseInformation.class);
       Sort purchaseDateDB = new Sort(Sort.Direction.ASC, "biValue");
       Pageable page = PageRequest.of(baseInformationParam.getPage()-1, baseInformationParam.getLimit(), purchaseDateDB);
       Page<SysBaseInformation> allSysBaseInformation = sysBaseInformationDao.findAll(spec, page);
       return allSysBaseInformation;
   }

    public List<SysBaseInformation> listSysBaseInformation(BaseInformationParam baseInformationParam){
        List<SearchFilter> filters = Lists.newArrayList();
        if(!StringUtil.isEmpty(baseInformationParam.getTypeId()))
            filters.add(new SearchFilter("biTypeId", SearchFilter.Operator.EQ, baseInformationParam.getTypeId()));
        if(!StringUtil.isEmpty(baseInformationParam.getType()))
            filters.add(new SearchFilter("biType", SearchFilter.Operator.LIKE, baseInformationParam.getType()));
        if(!StringUtil.isEmpty(baseInformationParam.getaMemo()))
            filters.add(new SearchFilter("aMemo", SearchFilter.Operator.EQ, baseInformationParam.getaMemo()));
        Specification<SysBaseInformation> spec = DynamicSpecifications.bySearchFilter(filters, SysBaseInformation.class);
        Sort purchaseDateDB = new Sort(Sort.Direction.ASC, "biValue");
        List<SysBaseInformation> allSysBaseInformation = sysBaseInformationDao.findAll(spec, purchaseDateDB);
        return allSysBaseInformation;
    }

    public void delete(Users operatorUser, SysBaseInformation deleteSysBaseInformation) {
        if (deleteSysBaseInformation == null) {
            return;
        }
        LOGGER.info("SysBaseInformation {} has delete,operator user id is {},name is ", deleteSysBaseInformation.toString(), operatorUser.getId(), operatorUser.getUserName());
        sysBaseInformationDao.delete(deleteSysBaseInformation);
    }
    public void delete(Users operatorUser, List<SysBaseInformation> deleteSysBaseInformationList) {
        if (deleteSysBaseInformationList == null) {
            return;
        }
        LOGGER.info("SysBaseInformation {} has delete,operator user id is {},name is ", deleteSysBaseInformationList.toString(), operatorUser.getId(), operatorUser.getUserName());
        sysBaseInformationDao.deleteAll(deleteSysBaseInformationList);
    }
}
