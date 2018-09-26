package com.xmrbi.hwgreensystem.service;


import com.google.common.collect.Lists;
import com.xmrbi.hwgreensystem.dao.RoleDao;
import com.xmrbi.hwgreensystem.dao.UsersDao;
import com.xmrbi.hwgreensystem.dao.util.DynamicSpecifications;
import com.xmrbi.hwgreensystem.dao.util.SearchFilter;
import com.xmrbi.hwgreensystem.domain.db.Role;
import com.xmrbi.hwgreensystem.domain.db.SysPlaza;
import com.xmrbi.hwgreensystem.domain.db.Users;
import com.xmrbi.hwgreensystem.domain.param.UserParam;
import com.xmrbi.hwgreensystem.until.Query;
import com.xmrbi.hwgreensystem.until.StringUtil;
import com.xmrbi.hwgreensystem.until.treeUtils.TreeListUtils;
import freemarker.template.utility.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by yangjb on 2017/7/16.
 * helloWorld
 */
@Service
public class UsersService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersService.class);
    @Resource
    private UsersDao usersDao;
    @Resource
    private RoleDao roleDao;
    @Resource
    private SysPlazaService sysPlazaService;


    public Users findByName(String name) {
        return usersDao.findByUserNo(name);
    }

    public Users findById(Long id){
        Optional<Users> optionalUsers=usersDao.findById(id);

        return optionalUsers.get();
    }


    public List<Users> findBySysPlaza(SysPlaza sysPlaza) {
        return usersDao.findBySysPlaza(sysPlaza);
    }


    public Users saveOrUpdate(Users users) {
        return usersDao.save(users);
    }

    public Role findByRoleId(Long id){
        Optional<Role> optionalRole=roleDao.findById(id);
        return optionalRole.get();
    }
    /**
     * 角色（除了超级管理员）
     * @return
     */
    public List<Role> listAllRole() {
        List<SearchFilter> filters = Lists.newArrayList();
        filters.add(new SearchFilter("id", SearchFilter.Operator.NEQ, 1));
        Specification<Role> spec = DynamicSpecifications.bySearchFilter(filters, Role.class);
        return Lists.newArrayList(roleDao.findAll(spec,new Sort(Sort.Direction.ASC, "id")));
    }


    public Page<Users> pageUsers(UserParam userParam){
        List<SearchFilter> filters = Lists.newArrayList();
        if(null!=userParam.getPlazaId()) {
            List<SysPlaza> sysPlazas=sysPlazaService.list();
            TreeListUtils treeListUtils=new TreeListUtils();
            List<Long> plazaIds=treeListUtils.treeSysPlazaList(sysPlazas,userParam.getPlazaId());
            plazaIds.add(userParam.getPlazaId());
            filters.add(new SearchFilter("sysPlaza.plazaId", SearchFilter.Operator.IN, plazaIds));
        }
        if(!StringUtil.isEmpty(userParam.getName())){
            filters.add(new SearchFilter("userNo"+SearchFilter.OR_SEPARATOR+"userName", SearchFilter.Operator.LIKE, userParam.getName()));
        }
        //filters.add(new SearchFilter("status", SearchFilter.Operator.EQ, 1));
        Specification<Users> spec = DynamicSpecifications.bySearchFilter(filters, Users.class);
        Sort purchaseDateDB = new Sort(Sort.Direction.DESC, "gmtCreate");
        Pageable page = PageRequest.of(userParam.getPage()-1, userParam.getLimit(), purchaseDateDB);
        Page<Users> allUsers = usersDao.findAll(spec, page);
        return allUsers;
    }

    public List<Users> listUsers(UserParam userParam){
        List<SearchFilter> filters = Lists.newArrayList();
        if(null!=userParam.getPlazaId())
            filters.add(new SearchFilter("sysPlaza.plazaId", SearchFilter.Operator.EQ, userParam.getPlazaId()));
        filters.add(new SearchFilter("status", SearchFilter.Operator.EQ, 1));
        Specification<Users> spec = DynamicSpecifications.bySearchFilter(filters, Users.class);
        /*Sort purchaseDateDB = new Sort(Sort.Direction.DESC, "gmtCreate");*/
        List<Users> allUsers = usersDao.findAll(spec);
        return allUsers;
    }

    public void delete(Users operatorUser, Users deleteUser) {
        if (deleteUser == null) {
            return;
        }
        LOGGER.info("users {} has delete,operator user id is {},name is ", deleteUser, operatorUser.getId(), operatorUser.getUserName());
        usersDao.delete(deleteUser);
    }
    public void delete(Users operatorUser, List<Users> deleteUserList) {
        if (deleteUserList == null) {
            return;
        }
        LOGGER.info("users {} has delete,operator user id is {},name is ", deleteUserList.toString(), operatorUser.getId(), operatorUser.getUserName());
        usersDao.deleteAll(deleteUserList);
    }
}
