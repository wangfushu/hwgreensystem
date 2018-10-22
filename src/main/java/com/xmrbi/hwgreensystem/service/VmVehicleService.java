package com.xmrbi.hwgreensystem.service;


import com.google.common.collect.Lists;
import com.xmrbi.hwgreensystem.common.BeanConfig;
import com.xmrbi.hwgreensystem.dao.SysConfigDao;
import com.xmrbi.hwgreensystem.dao.VmVehicleDao;
import com.xmrbi.hwgreensystem.dao.util.DynamicSpecifications;
import com.xmrbi.hwgreensystem.dao.util.SearchFilter;
import com.xmrbi.hwgreensystem.domain.db.SysBaseInformation;
import com.xmrbi.hwgreensystem.domain.db.SysConfig;
import com.xmrbi.hwgreensystem.domain.db.Users;
import com.xmrbi.hwgreensystem.domain.db.VmVehicle;
import com.xmrbi.hwgreensystem.domain.param.AndroidVehicleParam;
import com.xmrbi.hwgreensystem.domain.param.StatisticalReportParam;
import com.xmrbi.hwgreensystem.domain.param.VehicleParam;
import com.xmrbi.hwgreensystem.domain.param.VmVehicleQueryParam;
import com.xmrbi.hwgreensystem.domain.vo.StatisticalReportVo;
import com.xmrbi.hwgreensystem.until.DateUtil;
import com.xmrbi.hwgreensystem.until.ExcelUtil;
import com.xmrbi.hwgreensystem.until.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
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
    @Autowired
    private SysPlazaService sysPlazaService;
    @PersistenceUnit
    private EntityManagerFactory emf;

    public VmVehicle findById(String vVehicleNo) {
        Optional<VmVehicle> vmVehicleOptional = vmVehicleDao.findById(vVehicleNo);
        return vmVehicleOptional.get();
    }


    public List<SysConfig> findSysConfig(String configName) {
        return sysConfigDao.findByCfConfigNameOrderByCfConfigValueAsc(configName);
    }

    public SysConfig findSysConfigByNameAndValue(String configName, String configValue) {
        return sysConfigDao.findByCfConfigNameAndAndCfConfigValue(configName, configValue);
    }

    public Page<VmVehicle> listAndroid(VmVehicleQueryParam queryParam, Users users, int pageNo, int pageSize) {
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
            List<Long> plazaIds = sysPlazaService.findPlazaIdByParentId(users.getSysPlaza().getPlazaId());
            plazaIds.add(users.getSysPlaza().getPlazaId());
            filters.add(new SearchFilter("plazaId", SearchFilter.Operator.IN, plazaIds));
        }

        if (null != queryParam.getStartPassTime() && !"".equals(queryParam.getStartPassTime())) {
            filters.add(new SearchFilter("shiftDate", SearchFilter.Operator.GTE, DateUtil.getStartDate(DateUtil.fromDateStringToYMDDate(queryParam.getStartPassTime()))));
        }

        if (null != queryParam.getEndPassTime() && !"".equals(queryParam.getEndPassTime())) {
            filters.add(new SearchFilter("shiftDate", SearchFilter.Operator.LTE, DateUtil.getEndDate(DateUtil.fromDateStringToYMDDate(queryParam.getEndPassTime()))));
        }

        Specification<VmVehicle> spec = DynamicSpecifications.bySearchFilter(filters, VmVehicle.class);

        PageRequest page = PageRequest.of(pageNo - 1, pageSize, new Sort(Sort.Direction.DESC, "inputTime"));
        Page<VmVehicle> vmVehicles = vmVehicleDao.findAll(spec, page);
        return vmVehicles;
    }


    public VmVehicle save(VmVehicle vmVehicleForm, Users operateUsers) {
        VmVehicle vmVehicle = vmVehicleDao.save(vmVehicleForm);
        LOGGER.info(" vmVehicle {} has SAVE,operator user id is {},name is {}", vmVehicleForm.toString(), operateUsers.getId(), operateUsers.getUserNo());
        return vmVehicle;
    }

    public Page<VmVehicle> pageVmVehicle(VehicleParam vehicleParam, Users users) {
        List<SearchFilter> filters = Lists.newArrayList();
        if (!StringUtil.isEmpty(vehicleParam.getPlateNo())) {
            filters.add(new SearchFilter("plateNo", SearchFilter.Operator.LIKE, vehicleParam.getPlateNo()));
        }
        if (!StringUtil.isEmpty(vehicleParam.getPlazaName())) {
            filters.add(new SearchFilter("plazaName", SearchFilter.Operator.LIKE, vehicleParam.getPlazaName()));
        }
        if (null != vehicleParam.getPlazaId()) {
            List<Long> plazaIds = sysPlazaService.findPlazaIdByParentId(vehicleParam.getPlazaId());
            plazaIds.add(vehicleParam.getPlazaId());
            filters.add(new SearchFilter("plazaId", SearchFilter.Operator.IN, plazaIds));
        } else {
            List<Long> plazaIds = sysPlazaService.findPlazaIdByParentId(users.getSysPlaza().getPlazaId());
            plazaIds.add(users.getSysPlaza().getPlazaId());
            filters.add(new SearchFilter("plazaId", SearchFilter.Operator.IN, plazaIds));
        }

        if (null != vehicleParam.getType()) {
            filters.add(new SearchFilter("type", SearchFilter.Operator.EQ, vehicleParam.getType()));
        }
        if (null != users) {
            List<Long> plazaIds = sysPlazaService.findPlazaIdByParentId(users.getSysPlaza().getPlazaId());
            plazaIds.add(users.getSysPlaza().getPlazaId());
            filters.add(new SearchFilter("plazaId", SearchFilter.Operator.IN, plazaIds));
        }
        if (vehicleParam.getTimefromFormat() != null) {
            filters.add(new SearchFilter("shiftDate", SearchFilter.Operator.GTE, vehicleParam.getTimefromFormat()));
        }
        if (vehicleParam.getTimetoFormat() != null) {
            filters.add(new SearchFilter("shiftDate", SearchFilter.Operator.LTE, new Date(DateUtil.dayEndnTime(vehicleParam.getTimetoFormat()))));
        }
        Specification<VmVehicle> spec = DynamicSpecifications.bySearchFilter(filters, VmVehicle.class);
        Sort purchaseDateDB = new Sort(Sort.Direction.DESC, "inputTime");
        Pageable page = PageRequest.of(vehicleParam.getPage() - 1, vehicleParam.getLimit(), purchaseDateDB);
        Page<VmVehicle> allVehicles = vmVehicleDao.findAll(spec, page);
        return allVehicles;
    }

    /**
     * 车辆信息统计报表年表
     *
     * @param param
     * @param sysBaseInformations
     * @param users
     * @return
     */
    public List<StatisticalReportVo> findYearStatisticalVehicle(StatisticalReportParam param, List<SysBaseInformation> sysBaseInformations, Users users) {
        EntityManager em = emf.createEntityManager();
        //定义SQL
        String sql = "SELECT TOP " + param.getLimit() + " * from (select  ROW_NUMBER() OVER (ORDER BY plazaName) AS RowNumber, " +
                " plazaName,";
        for (SysBaseInformation sysBaseInformation : sysBaseInformations) {
            sql += " sum(case when [type]=" + sysBaseInformation.getBiValue() + " then 1 else 0 end) as '" + sysBaseInformation.getBiDescription() + "免征数量', " +
                    "sum(case when [type]=" + sysBaseInformation.getBiValue() + " then isnull(freeFee,0) else 0 end) as '" + sysBaseInformation.getBiDescription() + "免征金额',";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += "from vehicle where plazaId in ";
        List<Long> plazaIds = null;
        if (null != param.getPlazaId()) {
            plazaIds = sysPlazaService.findPlazaIdByParentId(param.getPlazaId());
            plazaIds.add(param.getPlazaId());
        } else {
            plazaIds = sysPlazaService.findPlazaIdByParentId(users.getSysPlaza().getPlazaId());
            plazaIds.add(users.getSysPlaza().getPlazaId());
        }
        String inStr = "";
        for (int i = 0; i < plazaIds.size(); i++) {
            inStr = inStr + String.valueOf(plazaIds.get(i)) + ",";
        }
        sql += "(" + inStr.substring(0, inStr.length() - 1) + ")";


        sql += "and convert(char(4),shiftDate,120)='" + param.getDateTime() + "' group by plazaName";
        sql += ")as temp WHERE RowNumber > " + param.getLimit() + "* (" + param.getPage() + "-1)";
        //创建原生SQL查询QUERY实例
        Query query = em.createNativeQuery(sql);
        //执行查询，返回的是对象数组(Object[])列表,
        //每一个对象数组存的是相应的实体属性
        List<Object[]> objectList = query.getResultList();
        List<StatisticalReportVo> statisticalReportVos = Lists.newArrayList();
        for (Object[] obj : objectList) {
            StatisticalReportVo statisticalReportVo = new StatisticalReportVo();
            if (null != obj[1])
                statisticalReportVo.setPlazaName(String.valueOf(obj[1]));
            HashMap<String, String> result = new HashMap<String, String>();
            int tempNum = 2;
            for (int i = 0; i < sysBaseInformations.size(); i++) {
                if (null != obj[tempNum]) {
                    result.put("num" + sysBaseInformations.get(i).getBiValue(), String.valueOf(obj[tempNum]));
                    tempNum++;
                }
                if (null != obj[tempNum]) {
                    result.put("fee" + sysBaseInformations.get(i).getBiValue(), String.valueOf(obj[tempNum]));
                    tempNum++;
                }
            }
            statisticalReportVo.setResult(result);
            statisticalReportVos.add(statisticalReportVo);
        }
        em.close();

        return statisticalReportVos;
    }

    /**
     * @param param
     * @param sysBaseInformations
     * @param users
     * @return
     */
    public Integer findCountYearStatisticalVehicle(StatisticalReportParam param, List<SysBaseInformation> sysBaseInformations, Users users) {
        EntityManager em = emf.createEntityManager();
        //定义SQL
        String sql = "SELECT count (*) from (select  ROW_NUMBER() OVER (ORDER BY plazaName) AS RowNumber, " +
                " plazaName,";
        for (SysBaseInformation sysBaseInformation : sysBaseInformations) {
            sql += " sum(case when [type]=" + sysBaseInformation.getBiValue() + " then 1 else 0 end) as '" + sysBaseInformation.getBiDescription() + "免征数量', " +
                    "sum(case when [type]=" + sysBaseInformation.getBiValue() + " then isnull(freeFee,0) else 0 end) as '" + sysBaseInformation.getBiDescription() + "免征金额',";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += "from vehicle where plazaId in (" + users.getSysPlaza().getPlazaId() + ") and convert(char(4),shiftDate,120)='" + param.getDateTime() + "' group by plazaName";
        sql += ")as temp ";
        //创建原生SQL查询QUERY实例
        Query query = em.createNativeQuery(sql);
        //执行查询，返回的是对象数组(Object[])列表,
        //每一个对象数组存的是相应的实体属性
        List<Object> objecArraytList = query.getResultList();
        Integer count = 0;
        for (Object obj : objecArraytList) {
            if (null != obj)
                count = Integer.valueOf(String.valueOf(obj));
        }
        em.close();

        return count;
    }


    /**
     * 安卓统计图表报表
     *
     * @param androidVehicleParam
     * @param sysBaseInformations
     * @param users
     * @return
     */
    public List<StatisticalReportVo> AndroidStatisticalVehicle(AndroidVehicleParam androidVehicleParam, List<SysBaseInformation> sysBaseInformations, Users users) {
        EntityManager em = emf.createEntityManager();
        //定义SQL
        String sql = "select plazaName,";
        for (SysBaseInformation sysBaseInformation : sysBaseInformations) {
            sql += " sum(case when [type]=" + sysBaseInformation.getBiValue() + " then 1 else 0 end) as '" + sysBaseInformation.getBiDescription() + "免征数量', " +
                    "sum(case when [type]=" + sysBaseInformation.getBiValue() + " then isnull(freeFee,0) else 0 end) as '" + sysBaseInformation.getBiDescription() + "免征金额',";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += "from vehicle where plazaId=" + users.getSysPlaza().getPlazaId();

        if (!StringUtil.isEmpty(androidVehicleParam.getStartPassTime())) {
            sql += " and shiftDate >='" + DateUtil.formatDate(DateUtil.parseDate(androidVehicleParam.getStartPassTime(), "yyyy-MM-dd"), "yyyy-MM-dd HH:mm:ss") + "'";
        }
        if (!StringUtil.isEmpty(androidVehicleParam.getEndPassTime())) {
            sql += " and shiftDate <='" + DateUtil.formatDate(DateUtil.getEndDate(DateUtil.parseDate(androidVehicleParam.getEndPassTime(), "yyyy-MM-dd")), "yyyy-MM-dd HH:mm:ss") + "'";
        }

        sql += " group by plazaName";
        //创建原生SQL查询QUERY实例
        Query query = em.createNativeQuery(sql);
        //执行查询，返回的是对象数组(Object[])列表,
        //每一个对象数组存的是相应的实体属性
        List<Object[]> objectList = query.getResultList();
        List<StatisticalReportVo> statisticalReportVos = Lists.newArrayList();
        for (Object[] obj : objectList) {
            StatisticalReportVo statisticalReportVo = new StatisticalReportVo();
            if (null != obj[0])
                statisticalReportVo.setPlazaName(String.valueOf(obj[1]));
            HashMap<String, String> result = new HashMap<String, String>();
            int tempNum = 1;
            for (int i = 0; i < sysBaseInformations.size(); i++) {
                if (null != obj[tempNum]) {
                    result.put("num" + sysBaseInformations.get(i).getBiValue(), String.valueOf(obj[tempNum]));
                    tempNum++;
                }
                if (null != obj[tempNum]) {
                    result.put("fee" + sysBaseInformations.get(i).getBiValue(), String.valueOf(obj[tempNum]));
                    tempNum++;
                }
            }
            statisticalReportVo.setResult(result);
            statisticalReportVos.add(statisticalReportVo);
        }
        em.close();

        return statisticalReportVos;
    }


    public List<StatisticalReportVo> findYearStatisticalVehicleList(StatisticalReportParam param, List<SysBaseInformation> sysBaseInformations, Users users) {
        EntityManager em = emf.createEntityManager();
        //定义SQL
        String sql = "select  " +
                " plazaName,";
        for (SysBaseInformation sysBaseInformation : sysBaseInformations) {
            sql += " sum(case when [type]=" + sysBaseInformation.getBiValue() + " then 1 else 0 end) as '" + sysBaseInformation.getBiDescription() + "免征数量', " +
                    "sum(case when [type]=" + sysBaseInformation.getBiValue() + " then isnull(freeFee,0) else 0 end) as '" + sysBaseInformation.getBiDescription() + "免征金额',";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += "from vehicle where plazaId in ";
        List<Long> plazaIds = null;
        if (null != param.getPlazaId()) {
            plazaIds = sysPlazaService.findPlazaIdByParentId(param.getPlazaId());
            plazaIds.add(param.getPlazaId());
        } else {
            plazaIds = sysPlazaService.findPlazaIdByParentId(users.getSysPlaza().getPlazaId());
            plazaIds.add(users.getSysPlaza().getPlazaId());
        }
        String inStr = "";
        for (int i = 0; i < plazaIds.size(); i++) {
            inStr = inStr + String.valueOf(plazaIds.get(i)) + ",";
        }
        sql += "(" + inStr.substring(0, inStr.length() - 1) + ")";


        sql += "and convert(char(4),shiftDate,120)='" + param.getDateTime() + "' group by plazaName";
        //创建原生SQL查询QUERY实例
        Query query = em.createNativeQuery(sql);
        //执行查询，返回的是对象数组(Object[])列表,
        //每一个对象数组存的是相应的实体属性
        List<Object[]> objectList = query.getResultList();
        List<StatisticalReportVo> statisticalReportVos = Lists.newArrayList();
        for (Object[] obj : objectList) {
            StatisticalReportVo statisticalReportVo = new StatisticalReportVo();
            if (null != obj[0])
                statisticalReportVo.setPlazaName(String.valueOf(obj[0]));
            HashMap<String, String> result = new HashMap<String, String>();
            int tempNum = 1;
            for (int i = 0; i < sysBaseInformations.size(); i++) {
                if (null != obj[tempNum]) {
                    result.put("num" + sysBaseInformations.get(i).getBiValue(), String.valueOf(obj[tempNum]));
                    tempNum++;
                }
                if (null != obj[tempNum]) {
                    result.put("fee" + sysBaseInformations.get(i).getBiValue(), String.valueOf(obj[tempNum]));
                    tempNum++;
                }
            }
            statisticalReportVo.setResult(result);
            statisticalReportVos.add(statisticalReportVo);
        }
        em.close();

        return statisticalReportVos;
    }

    public byte[] getVehicleYearStatisticalExcel(List<StatisticalReportVo> statisticalReportVos, String fileName, List<SysBaseInformation> Title) {
        if (CollectionUtils.isEmpty(statisticalReportVos)) {
            return null;
        }
        return ExcelUtil.exportReportExcel(statisticalReportVos, fileName, Title);
    }


    /**
     * 车辆信息统计报表月表
     *
     * @param param
     * @param sysBaseInformations
     * @param users
     * @return
     */
    public List<StatisticalReportVo> findMonthStatisticalVehicle(StatisticalReportParam param, List<SysBaseInformation> sysBaseInformations, Users users) {
        EntityManager em = emf.createEntityManager();
        //定义SQL
        String sql = "SELECT TOP " + param.getLimit() + " * from (select  ROW_NUMBER() OVER (ORDER BY plazaName) AS RowNumber, " +
                " plazaName,";
        for (SysBaseInformation sysBaseInformation : sysBaseInformations) {
            sql += " sum(case when [type]=" + sysBaseInformation.getBiValue() + " then 1 else 0 end) as '" + sysBaseInformation.getBiDescription() + "免征数量', " +
                    "sum(case when [type]=" + sysBaseInformation.getBiValue() + " then isnull(freeFee,0) else 0 end) as '" + sysBaseInformation.getBiDescription() + "免征金额',";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += "from vehicle where plazaId in ";
        List<Long> plazaIds = null;
        if (null != param.getPlazaId()) {
            plazaIds = sysPlazaService.findPlazaIdByParentId(param.getPlazaId());
            plazaIds.add(param.getPlazaId());
        } else {
            plazaIds = sysPlazaService.findPlazaIdByParentId(users.getSysPlaza().getPlazaId());
            plazaIds.add(users.getSysPlaza().getPlazaId());
        }
        String inStr = "";
        for (int i = 0; i < plazaIds.size(); i++) {
            inStr = inStr + String.valueOf(plazaIds.get(i)) + ",";
        }
        sql += "(" + inStr.substring(0, inStr.length() - 1) + ")";


        sql += "and convert(char(7),shiftDate,120)='" + param.getDateTime() + "' group by plazaName";
        sql += ")as temp WHERE RowNumber > " + param.getLimit() + "* (" + param.getPage() + "-1)";
        //创建原生SQL查询QUERY实例
        Query query = em.createNativeQuery(sql);
        //执行查询，返回的是对象数组(Object[])列表,
        //每一个对象数组存的是相应的实体属性
        List<Object[]> objectList = query.getResultList();
        List<StatisticalReportVo> statisticalReportVos = Lists.newArrayList();
        for (Object[] obj : objectList) {
            StatisticalReportVo statisticalReportVo = new StatisticalReportVo();
            if (null != obj[1])
                statisticalReportVo.setPlazaName(String.valueOf(obj[1]));
            HashMap<String, String> result = new HashMap<String, String>();
            int tempNum = 2;
            for (int i = 0; i < sysBaseInformations.size(); i++) {
                if (null != obj[tempNum]) {
                    result.put("num" + sysBaseInformations.get(i).getBiValue(), String.valueOf(obj[tempNum]));
                    tempNum++;
                }
                if (null != obj[tempNum]) {
                    result.put("fee" + sysBaseInformations.get(i).getBiValue(), String.valueOf(obj[tempNum]));
                    tempNum++;
                }
            }
            statisticalReportVo.setResult(result);
            statisticalReportVos.add(statisticalReportVo);
        }
        em.close();

        return statisticalReportVos;
    }

    /**
     * 月统计报表条数
     *
     * @param param
     * @param sysBaseInformations
     * @param users
     * @return
     */
    public Integer findCountMonthStatisticalVehicle(StatisticalReportParam param, List<SysBaseInformation> sysBaseInformations, Users users) {
        EntityManager em = emf.createEntityManager();
        //定义SQL
        String sql = "SELECT count (*) from (select  ROW_NUMBER() OVER (ORDER BY plazaName) AS RowNumber, " +
                " plazaName,";
        for (SysBaseInformation sysBaseInformation : sysBaseInformations) {
            sql += " sum(case when [type]=" + sysBaseInformation.getBiValue() + " then 1 else 0 end) as '" + sysBaseInformation.getBiDescription() + "免征数量', " +
                    "sum(case when [type]=" + sysBaseInformation.getBiValue() + " then isnull(freeFee,0) else 0 end) as '" + sysBaseInformation.getBiDescription() + "免征金额',";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += "from vehicle where plazaId in (" + users.getSysPlaza().getPlazaId() + ") and convert(char(7),shiftDate,120)='" + param.getDateTime() + "' group by plazaName";
        sql += ")as temp ";
        //创建原生SQL查询QUERY实例
        Query query = em.createNativeQuery(sql);
        //执行查询，返回的是对象数组(Object[])列表,
        //每一个对象数组存的是相应的实体属性
        List<Object> objecArraytList = query.getResultList();
        Integer count = 0;
        for (Object obj : objecArraytList) {
            if (null != obj)
                count = Integer.valueOf(String.valueOf(obj));
        }
        em.close();

        return count;
    }


    public List<StatisticalReportVo> findMonthStatisticalVehicleList(StatisticalReportParam param, List<SysBaseInformation> sysBaseInformations, Users users) {
        EntityManager em = emf.createEntityManager();
        //定义SQL
        String sql = "select  " +
                " plazaName,";
        for (SysBaseInformation sysBaseInformation : sysBaseInformations) {
            sql += " sum(case when [type]=" + sysBaseInformation.getBiValue() + " then 1 else 0 end) as '" + sysBaseInformation.getBiDescription() + "免征数量', " +
                    "sum(case when [type]=" + sysBaseInformation.getBiValue() + " then isnull(freeFee,0) else 0 end) as '" + sysBaseInformation.getBiDescription() + "免征金额',";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += "from vehicle where plazaId in ";
        List<Long> plazaIds = null;
        if (null != param.getPlazaId()) {
            plazaIds = sysPlazaService.findPlazaIdByParentId(param.getPlazaId());
            plazaIds.add(param.getPlazaId());
        } else {
            plazaIds = sysPlazaService.findPlazaIdByParentId(users.getSysPlaza().getPlazaId());
            plazaIds.add(users.getSysPlaza().getPlazaId());
        }
        String inStr = "";
        for (int i = 0; i < plazaIds.size(); i++) {
            inStr = inStr + String.valueOf(plazaIds.get(i)) + ",";
        }
        sql += "(" + inStr.substring(0, inStr.length() - 1) + ")";


        sql += "and convert(char(7),shiftDate,120)='" + param.getDateTime() + "' group by plazaName";
        //创建原生SQL查询QUERY实例
        Query query = em.createNativeQuery(sql);
        //执行查询，返回的是对象数组(Object[])列表,
        //每一个对象数组存的是相应的实体属性
        List<Object[]> objectList = query.getResultList();
        List<StatisticalReportVo> statisticalReportVos = Lists.newArrayList();
        for (Object[] obj : objectList) {
            StatisticalReportVo statisticalReportVo = new StatisticalReportVo();
            if (null != obj[0])
                statisticalReportVo.setPlazaName(String.valueOf(obj[0]));
            HashMap<String, String> result = new HashMap<String, String>();
            int tempNum = 1;
            for (int i = 0; i < sysBaseInformations.size(); i++) {
                if (null != obj[tempNum]) {
                    result.put("num" + sysBaseInformations.get(i).getBiValue(), String.valueOf(obj[tempNum]));
                    tempNum++;
                }
                if (null != obj[tempNum]) {
                    result.put("fee" + sysBaseInformations.get(i).getBiValue(), String.valueOf(obj[tempNum]));
                    tempNum++;
                }
            }
            statisticalReportVo.setResult(result);
            statisticalReportVos.add(statisticalReportVo);
        }
        em.close();

        return statisticalReportVos;
    }


    /*********************************************数据库操作*********************************************************************/

    private static final Map<String, IdPool> ID_MAP = new ConcurrentHashMap<String, IdPool>();
    private int poosize = 2;

    public synchronized String generate(String serialNumber, String objString) throws HibernateException {

        // String dayStr= DateUtils.getCurrDateStr(1);
        String entityName = objString + serialNumber;
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("需要获取主键的实体名:[" + entityName + "]");
        }
        if (ID_MAP.get(entityName) == null) {
            initalize(entityName);
        }
        String id = serialNumber + ID_MAP.get(entityName).getNextId();
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
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);
        IdPool pool = context.getBean(IdPool.class);
        pool.setEntityName(entityName);
        pool.setPoolSize(poosize);
        pool.initalize();
        //IdPool pool = new IdPool(entityName, poosize);
        ID_MAP.put(entityName, pool);
    }

/*********************************************************************************************************************/
}
