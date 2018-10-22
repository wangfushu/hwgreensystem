package com.xmrbi.hwgreensystem.controller;

import com.xmrbi.hwgreensystem.domain.db.SysBaseInformation;
import com.xmrbi.hwgreensystem.domain.db.SysConfig;
import com.xmrbi.hwgreensystem.domain.db.Users;
import com.xmrbi.hwgreensystem.domain.db.VmVehicle;
import com.xmrbi.hwgreensystem.domain.form.VmVehicleForm;
import com.xmrbi.hwgreensystem.domain.param.BaseInformationParam;
import com.xmrbi.hwgreensystem.domain.param.StatisticalReportParam;
import com.xmrbi.hwgreensystem.domain.param.VehicleParam;
import com.xmrbi.hwgreensystem.domain.vo.StatisticalReportVo;
import com.xmrbi.hwgreensystem.service.BaseInformationService;
import com.xmrbi.hwgreensystem.service.SysPlazaService;
import com.xmrbi.hwgreensystem.service.UsersService;
import com.xmrbi.hwgreensystem.service.VmVehicleService;
import com.xmrbi.hwgreensystem.until.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
 * @description :
 * ---------------------------------
 * @Author: wangfushu
 * @Date: 2018-09-27 16:04
 */
@Controller
@RequestMapping(value = "/report")
@Scope("prototype")
public class StatisticalController extends BaseControl {
    private Logger logger = LoggerFactory.getLogger(StatisticalController.class);
    @Autowired
    VmVehicleService vmVehicleService;
    @Autowired
    BaseInformationService baseInformationService;
    @Autowired
    UsersService usersService;
    @Autowired
    SysPlazaService sysPlazaService;


    @RequestMapping(value = "/vehicle-report")
    public String vehiclereport(VehicleParam vehicleParam, Model model) {
        Users currentUser = getCurrentUser();
        if (StringUtil.isEmpty(vehicleParam.getTimefrom()) && StringUtil.isEmpty(vehicleParam.getTimeto())) {
            vehicleParam.setTimefrom(DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
            vehicleParam.setTimeto(DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
        }
        BaseInformationParam baseInformationParam = new BaseInformationParam();
        baseInformationParam.setType("freeType");
        baseInformationParam.setaMemo("1");
        model.addAttribute("sysBaseInformations", baseInformationService.listSysBaseInformation(baseInformationParam));
        model.addAttribute("sysPlazaList", sysPlazaService.findLevel3(currentUser.getSysPlaza().getPlazaId(), 3l));
        model.addAttribute("param", vehicleParam);
        return "/statistical/vehicle-report";
    }

    @RequestMapping(value = "/year-report")
    public String yearreport(StatisticalReportParam param, Model model) {
        Users currentUser = getCurrentUser();
        if (StringUtil.isEmpty(param.getDateTime())) {
            param.setDateTime(DateUtil.formatDate(new Date(), "yyyy"));

        }
        model.addAttribute("sysPlazaList", sysPlazaService.findLevel3(currentUser.getSysPlaza().getPlazaId(), 3l));
        model.addAttribute("param", param);
        return "/statistical/statistical-year-report";
    }

    @RequestMapping(value = "/month-report")
    public String monthreport(StatisticalReportParam param, Model model) {
        Users currentUser = getCurrentUser();
        if (StringUtil.isEmpty(param.getDateTime())) {
            param.setDateTime(DateUtil.formatDate(new Date(), "yyyy-MM"));

        }
        model.addAttribute("sysPlazaList", sysPlazaService.findLevel3(currentUser.getSysPlaza().getPlazaId(), 3l));
        model.addAttribute("param", param);
        return "/statistical/statistical-month-report";
    }


    @RequestMapping(value = "/picture-show/{vehicleNo}")
    public String picdtureshow(@PathVariable("vehicleNo") String vehicleNo, Model model) {
        Users currentUser = getCurrentUser();
        VmVehicle vmVehicle = vmVehicleService.findById(vehicleNo);
        BaseInformationParam baseInformationParam = new BaseInformationParam();
        baseInformationParam.setType("freeType");
        baseInformationParam.setaMemo("1");
        List<SysBaseInformation> sysBaseInformations = baseInformationService.listSysBaseInformation(baseInformationParam);
        model.addAttribute("vmVehicle", vmVehicle);
        model.addAttribute("sysBaseInformations", sysBaseInformations);
        return "/statistical/picture-show";
    }

    @RequestMapping(value = "/only_photo_show/{vehicleNo}")
    public String onlyphotoshow(@PathVariable("vehicleNo") String vehicleNo, Model model) {
        Users currentUser = getCurrentUser();
        VmVehicle vmVehicle = vmVehicleService.findById(vehicleNo);
        model.addAttribute("vmVehicle", vmVehicle);
        return "/statistical/only_photo_show";
    }

    @ResponseBody
    @RequestMapping(value = "/saveVehicle", method = RequestMethod.POST)
    public WholeResponse saveVehicle(VmVehicleForm vmVehicleForm) {
        try {
            Users users = getCurrentUser();
            VmVehicle vmVehicle = vmVehicleService.findById(vmVehicleForm.getVehicleNo());
            vmVehicle.setUserNo(users.getUserNo());
            vmVehicle.setUserName(users.getUserName());
            vmVehicle.setUserId(users.getId());
            if (!StringUtil.isEmpty(vmVehicleForm.getPlateNo()))
                vmVehicle.setPlateNo(vmVehicleForm.getPlateNo());
            if (!StringUtil.isEmpty(vmVehicleForm.getPlateColor()))
                vmVehicle.setPlateColor(vmVehicleForm.getPlateColor());
            if (null != vmVehicleForm.getType())
                vmVehicle.setType(vmVehicleForm.getType());
            if (null != vmVehicleForm.getCapacity())
                vmVehicle.setCapacity(vmVehicleForm.getCapacity());
            if (null != vmVehicleForm.getCalCapacity())
                vmVehicle.setCalCapacity(vmVehicleForm.getCalCapacity());
            if (null != vmVehicleForm.getPeccNum())
                vmVehicle.setPeccNum(vmVehicleForm.getPeccNum());
            if (null != vmVehicleForm.getFreeFee())
                vmVehicle.setFreeFee(vmVehicleForm.getFreeFee());
            //oldUser.setEmail(users.getEmail());
            if (null != vmVehicleForm.getRealFee())
                vmVehicle.setRealFee(vmVehicleForm.getRealFee());
            if (!StringUtil.isEmpty(vmVehicleForm.getCustomerName()))
                vmVehicle.setCustomerName(vmVehicleForm.getCustomerName());
            if (!StringUtil.isEmpty(vmVehicleForm.getTransactorPhone()))
                vmVehicle.setTransactorPhone(vmVehicleForm.getTransactorPhone());

            if (!StringUtil.isEmpty(vmVehicleForm.getRemark()))
                vmVehicle.setRemark(vmVehicleForm.getRemark());
            if (!StringUtil.isEmpty(vmVehicleForm.getCondition()))
                vmVehicle.setCondition(vmVehicleForm.getCondition());
            if (!StringUtil.isEmpty(vmVehicleForm.getProductName()))
                vmVehicle.setProductName(vmVehicleForm.getProductName());

            vmVehicle.setUpdateSign(1l);
            vmVehicle.setModifyTime(new Date());

            vmVehicleService.save(vmVehicle, users);
            return WholeResponse.successResponse("更新数据成功");

        } catch (Exception e) {
            logger.error("saveUser保存出错" + e.getMessage());
            return WholeResponse.errorResponse("500", "系统异常");
        }
    }


    @RequestMapping(value = "/listVehicle")
    @ResponseBody
    public PageUtils listVehicle(VehicleParam vehicleParam, Long userId, HttpServletRequest request, HttpServletResponse response) {
        try {
            Users users;
            if (null != userId) {
                users = usersService.findById(userId);
            } else {
                users = getCurrentUser();
            }
            //查询列表数据 对分页参数进行类型转换
            Page<VmVehicle> vmVehicles = vmVehicleService.pageVmVehicle(vehicleParam, users);
            for (VmVehicle vmVehicle : vmVehicles.getContent()) {
                SysBaseInformation sysBaseInformation = baseInformationService.findByBiTypeAndBiValue("freeType", String.valueOf(vmVehicle.getType()));
                SysConfig sysConfig = vmVehicleService.findSysConfigByNameAndValue("teamTime", String.valueOf(vmVehicle.getShiftID()));
                if (null != sysConfig) {
                    vmVehicle.setShiftName(sysConfig.getCfConfigDescription());
                }
                if (null != sysBaseInformation) {
                    vmVehicle.setTypeName(sysBaseInformation.getBiDescription());
                }

            }
            PageUtils pageUtils = new PageUtils(vmVehicles.getContent(), Integer.valueOf(String.valueOf(vmVehicles.getTotalElements())));
            return pageUtils;
        } catch (Exception e) {
            logger.info("listVehicle查询出错" + e.getMessage());
            return null;
        }
    }


    @RequestMapping(value = "/YearPageReport")
    @ResponseBody
    public PageUtils YearPageReport(StatisticalReportParam param, Long userId, HttpServletRequest request, HttpServletResponse response) {
        try {
            Users users;
            if (null != userId) {
                users = usersService.findById(userId);
            } else {
                users = getCurrentUser();
            }
            //查询列表数据 对分页参数进行类型转换
            BaseInformationParam baseInformationParam = new BaseInformationParam();
            baseInformationParam.setType("freeType");
            baseInformationParam.setaMemo("1");
            List<SysBaseInformation> sysBaseInformations = baseInformationService.listSysBaseInformation(baseInformationParam);
            List<StatisticalReportVo> vmVehicles = vmVehicleService.findYearStatisticalVehicle(param, sysBaseInformations, users);

            PageUtils pageUtils = new PageUtils(vmVehicles, vmVehicleService.findCountYearStatisticalVehicle(param, sysBaseInformations, users));
            return pageUtils;
        } catch (Exception e) {
            logger.info("listVehicleYear查询出错" + e.getMessage());
            return null;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/yearExport", method = RequestMethod.GET)
    public ResponseEntity yearExport(StatisticalReportParam param, Long userId) {
        Users users;
        if (null != userId) {
            users = usersService.findById(userId);
        } else {
            users = getCurrentUser();
        }

        String fileName = param.getDateTime() + "年_免征情况年统计报表.xls";
        BaseInformationParam baseInformationParam = new BaseInformationParam();
        baseInformationParam.setType("freeType");
        baseInformationParam.setaMemo("1");
        List<SysBaseInformation> sysBaseInformations = baseInformationService.listSysBaseInformation(baseInformationParam);
        List<StatisticalReportVo> statisticalReportVos = vmVehicleService.findYearStatisticalVehicleList(param, sysBaseInformations, users);

        byte[] tagInStoreExcel = vmVehicleService.getVehicleYearStatisticalExcel(statisticalReportVos, fileName, sysBaseInformations);
        if (tagInStoreExcel == null) {
            return new ResponseEntity(null, HttpStatus.NO_CONTENT);
        }
        try {
            return MyFileUtil.downloadFile(tagInStoreExcel, fileName, request);
        } catch (IOException e) {
            return new ResponseEntity(null, HttpStatus.NO_CONTENT);
        }
    }


    @RequestMapping(value = "/MonthPageReport")
    @ResponseBody
    public PageUtils MonthPageReport(StatisticalReportParam param, Long userId, HttpServletRequest request, HttpServletResponse response) {
        try {
            Users users;
            if (null != userId) {
                users = usersService.findById(userId);
            } else {
                users = getCurrentUser();
            }
            //查询列表数据 对分页参数进行类型转换
            BaseInformationParam baseInformationParam = new BaseInformationParam();
            baseInformationParam.setType("freeType");
            baseInformationParam.setaMemo("1");
            List<SysBaseInformation> sysBaseInformations = baseInformationService.listSysBaseInformation(baseInformationParam);
            List<StatisticalReportVo> vmVehicles = vmVehicleService.findMonthStatisticalVehicle(param, sysBaseInformations, users);

            PageUtils pageUtils = new PageUtils(vmVehicles, vmVehicleService.findCountMonthStatisticalVehicle(param, sysBaseInformations, users));
            return pageUtils;
        } catch (Exception e) {
            logger.info("listVehicleMonth查询出错" + e.getMessage());
            return null;
        }
    }


    @ResponseBody
    @RequestMapping(value = "/monthExport", method = RequestMethod.GET)
    public ResponseEntity monthExport(StatisticalReportParam param, Long userId) {
        Users users;
        if (null != userId) {
            users = usersService.findById(userId);
        } else {
            users = getCurrentUser();
        }

        String fileName = param.getDateTime() + "_免征情况月统计报表.xls";
        BaseInformationParam baseInformationParam = new BaseInformationParam();
        baseInformationParam.setType("freeType");
        baseInformationParam.setaMemo("1");
        List<SysBaseInformation> sysBaseInformations = baseInformationService.listSysBaseInformation(baseInformationParam);
        List<StatisticalReportVo> statisticalReportVos = vmVehicleService.findMonthStatisticalVehicleList(param, sysBaseInformations, users);

        byte[] tagInStoreExcel = vmVehicleService.getVehicleYearStatisticalExcel(statisticalReportVos, fileName, sysBaseInformations);
        //byte[] BmBillToComputerExcel = fmFeeService.getBmBillToComputerExcel(bmBillToComputers,fileName,Title);
        if (tagInStoreExcel == null) {
            return new ResponseEntity(null, HttpStatus.NO_CONTENT);
        }
        try {
            return MyFileUtil.downloadFile(tagInStoreExcel, fileName, request);
        } catch (IOException e) {
            return new ResponseEntity(null, HttpStatus.NO_CONTENT);
        }
    }

}
