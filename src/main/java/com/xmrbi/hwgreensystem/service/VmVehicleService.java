package com.xmrbi.hwgreensystem.service;


import com.google.common.collect.Lists;
import com.xmrbi.hwgreensystem.dao.SysConfigDao;
import com.xmrbi.hwgreensystem.dao.VmVehicleDao;
import com.xmrbi.hwgreensystem.dao.util.DynamicSpecifications;
import com.xmrbi.hwgreensystem.dao.util.SearchFilter;
import com.xmrbi.hwgreensystem.domain.db.SysConfig;
import com.xmrbi.hwgreensystem.domain.db.SysPlaza;
import com.xmrbi.hwgreensystem.domain.db.Users;
import com.xmrbi.hwgreensystem.domain.db.VmVehicle;
import com.xmrbi.hwgreensystem.domain.param.UserParam;
import com.xmrbi.hwgreensystem.domain.param.VmVehicleQueryParam;
import com.xmrbi.hwgreensystem.until.DateUtil;
import com.xmrbi.hwgreensystem.until.StringUtil;
import com.xmrbi.hwgreensystem.until.treeUtils.TreeListUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by wangfs on 2017/6/28. helloWorld
 */
@Service
public class VmVehicleService {
    private Logger LOGGER = LoggerFactory.getLogger(VmVehicleService.class);

    @Autowired
    private VmVehicleDao vmVehicleDao;
    @Autowired
    private SysConfigDao sysConfigDao;
    @PersistenceUnit
    private EntityManagerFactory emf;

    public VmVehicle findById(String vVehicleNo){
        Optional<VmVehicle> vmVehicleOptional= vmVehicleDao.findById(vVehicleNo);
        return vmVehicleOptional.get();
    }


    public List<SysConfig> findSysConfig(String configName){
        return sysConfigDao.findByCfConfigNameOrderByCfConfigValueAsc(configName);
    }

    public Page<VmVehicle> listAndroid(VmVehicleQueryParam queryParam, Users users, int pageNo, int pageSize){
        List<SearchFilter> filters = Lists.newArrayList();

        if (null != queryParam.getPlateNo() && !"".equals(queryParam.getPlateNo())) {
            filters.add(new SearchFilter("plateNo", SearchFilter.Operator.LIKE, queryParam.getPlateNo()));
        }
        if (null != queryParam.getPlateColor() && !"".equals(queryParam.getPlateColor())) {
            filters.add(new SearchFilter("plateColor", SearchFilter.Operator.EQ, queryParam.getPlateColor()));

        }
        if (null != queryParam.getType() && !"".equals(String.valueOf(queryParam.getType()))) {
            filters.add(new SearchFilter("type", SearchFilter.Operator.EQ, queryParam.getType()));
        }

        if (null != users) {
            filters.add(new SearchFilter("vehicleNo", SearchFilter.Operator.TLIKE, StringUtil.getFormat(5,users.getSysPlaza().getPlazaId().intValue()) ));
        }

        if (null != queryParam.getStartPassTime() && !"".equals(queryParam.getStartPassTime())) {
            filters.add(new SearchFilter("shiftDate", SearchFilter.Operator.GTE, DateUtil.getStartDate(DateUtil.fromDateStringToYMDDate(queryParam.getStartPassTime()))));
        }

        if (null != queryParam.getEndPassTime() && !"".equals(queryParam.getEndPassTime())) {
            filters.add(new SearchFilter("shiftDate", SearchFilter.Operator.LTE, DateUtil.getEndDate(DateUtil.fromDateStringToYMDDate(queryParam.getEndPassTime()))));
        }

        Specification<VmVehicle> spec = DynamicSpecifications.bySearchFilter(filters, VmVehicle.class);

        PageRequest page = PageRequest.of(pageNo - 1, pageSize, new Sort(Sort.Direction.DESC, "shiftDate"));
        Page<VmVehicle> vmVehicles = vmVehicleDao.findAll(spec, page);
        return vmVehicles;
    }


    public VmVehicle save(VmVehicle vmVehicleForm, Users operateUsers){
        VmVehicle vmVehicle=vmVehicleDao.save(vmVehicleForm);
        LOGGER.info(" vmVehicle {} has SAVE,operator user id is {},name is ", vmVehicleForm.toString(), operateUsers.getId(), operateUsers.getUserName());
        return vmVehicle;
    }
    /*********************************************数据库操作*********************************************************************/

    private static final Map<String, IdPool> ID_MAP = new ConcurrentHashMap<String, IdPool>();
    private int poosize=2;
    public synchronized String generate(String serialNumber, String objString) throws HibernateException {

        // String dayStr= DateUtils.getCurrDateStr(1);
        String entityName = objString+serialNumber;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("需要获取主键的实体名:[" + entityName + "]");
        }
        if (ID_MAP.get(entityName) == null) {
            initalize(entityName);
        }
        String id = serialNumber+ID_MAP.get(entityName).getNextId();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("线程:[" + Thread.currentThread().getName() + "] " +
                    "实体:[" + entityName + "] 生成的主键为:[" + id + "]");
        }
        return id;
    }
    /**
     * 初始化对象的主键
     *
     * @param entityName 对象名称
     */
    private void initalize(String entityName) {
        IdPool pool = new IdPool(entityName, poosize);
        ID_MAP.put(entityName, pool);
    }

/*********************************************************************************************************************/
}
