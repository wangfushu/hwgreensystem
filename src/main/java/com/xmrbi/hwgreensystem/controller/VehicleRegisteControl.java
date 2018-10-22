package com.xmrbi.hwgreensystem.controller;


import com.xmrbi.hwgreensystem.domain.db.SysBaseInformation;
import com.xmrbi.hwgreensystem.domain.db.SysConfig;
import com.xmrbi.hwgreensystem.domain.db.Users;
import com.xmrbi.hwgreensystem.domain.db.VmVehicle;
import com.xmrbi.hwgreensystem.domain.param.AndroidVehicleParam;
import com.xmrbi.hwgreensystem.domain.param.BaseInformationParam;
import com.xmrbi.hwgreensystem.domain.param.VmVehicleQueryParam;
import com.xmrbi.hwgreensystem.domain.vo.StatisticalReportVo;
import com.xmrbi.hwgreensystem.service.BaseInformationService;
import com.xmrbi.hwgreensystem.service.UsersService;
import com.xmrbi.hwgreensystem.service.VmVehicleService;
import com.xmrbi.hwgreensystem.until.DateUtil;
import com.xmrbi.hwgreensystem.until.PageUtils;
import com.xmrbi.hwgreensystem.until.StringUtil;
import com.xmrbi.hwgreensystem.until.WholeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangfs on 2017-10-10 helloword.
 * <p/>
 * 车辆信息控制层
 */

@RequestMapping("/vehicle")
@RestController
public class VehicleRegisteControl extends BaseControl {

    private Logger LOGGER = LoggerFactory.getLogger(VehicleRegisteControl.class);

    @Autowired
    private VmVehicleService vmVehicleService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private BaseInformationService baseInformationService;

    @Value("${image_base_path}")
    private String base_path;
    @Value("${image_file_name}")
    private String image_file_name;

    @ResponseBody
    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    private WholeResponse VehicleRegiste(@RequestParam(value = "file1", required = false) MultipartFile file1, @RequestParam(value = "file2", required = false) MultipartFile file2, @RequestParam(value = "file3", required = false) MultipartFile file3, @RequestParam(value = "file4", required = false) MultipartFile file4,
                                         HttpServletRequest request) {
        try {
            //用户Id
            String userId = request.getParameter("userId");
            Users users = usersService.findById(Long.valueOf(userId));
            VmVehicle vmVehicleForm = new VmVehicle();
            if (null != users) {
                vmVehicleForm.setUserNo(users.getUserNo());
                vmVehicleForm.setUserName(users.getUserName());
                vmVehicleForm.setUserId(users.getId());
            }
            //车辆编号
            String vehicleNo = request.getParameter("vehicleNo");
            if (!StringUtil.isEmpty(vehicleNo)) {
                vmVehicleForm.setVehicleNo(request.getParameter("vehicleNo"));
            } else {
                //流水单号生成
                String id = new String();
                //employee
                String currDate = StringUtil.getFormat(5, users.getSysPlaza().getPlazaId().intValue()) + DateUtil.formatDate(new Date(), "yyyyMMdd");
                vmVehicleForm.setVehicleNo(vmVehicleService.generate(currDate, "VmVehicle.vehicleNo"));
            }
            //车牌号
            String plateNo = request.getParameter("plateNo");
            if (!StringUtil.isEmpty(plateNo))
                vmVehicleForm.setPlateNo(request.getParameter("plateNo").toUpperCase());
            //车牌颜色
            String plateColor = request.getParameter("plateColor");
            if (!StringUtil.isEmpty(plateColor))
                vmVehicleForm.setPlateColor(request.getParameter("plateColor"));
            //车型
            String type = request.getParameter("type");
            if (!StringUtil.isEmpty(type))
                vmVehicleForm.setType(Long.valueOf(request.getParameter("type")));
            //载量\
            String capacity = request.getParameter("capacity");
            if (!StringUtil.isEmpty(capacity))
                vmVehicleForm.setCapacity(Float.valueOf(request.getParameter("capacity")));
            //实际载量
            String calCapacity = request.getParameter("calCapacity");
            if (!StringUtil.isEmpty(calCapacity))
                vmVehicleForm.setCalCapacity(Float.valueOf(request.getParameter("calCapacity")));

            //违章次数
            String peccNum = request.getParameter("peccNum");
            if (!StringUtil.isEmpty(peccNum))
                vmVehicleForm.setPeccNum(Long.valueOf(request.getParameter("peccNum")));
            //免费金额
            String freeFee = request.getParameter("freeFee");
            if (!StringUtil.isEmpty(freeFee))
                vmVehicleForm.setFreeFee(Double.valueOf(request.getParameter("freeFee")));

            //实缴金额
            String realFee = request.getParameter("realFee");
            if (!StringUtil.isEmpty(realFee))
                vmVehicleForm.setRealFee(Double.valueOf(request.getParameter("realFee")));

            //车主姓名
            String customerName = request.getParameter("customerName");
            if (!StringUtil.isEmpty(customerName))
                vmVehicleForm.setCustomerName(request.getParameter("customerName"));
            //用户手机
            String transactorPhone = request.getParameter("transactorPhone");
            if (!StringUtil.isEmpty(transactorPhone))
                vmVehicleForm.setTransactorPhone(request.getParameter("transactorPhone"));
            //备注
            String remark = request.getParameter("remark");
            if (!StringUtil.isEmpty(remark))
                vmVehicleForm.setRemark(request.getParameter("remark"));
            //备注
            String condition = request.getParameter("condition");
            if (!StringUtil.isEmpty(condition))
                vmVehicleForm.setCondition(request.getParameter("condition"));
            //产品名称
            String productName = request.getParameter("productName");
            if (!StringUtil.isEmpty(productName))
                vmVehicleForm.setProductName(request.getParameter("productName"));
            //产品名称
            String plazaId = request.getParameter("plazaId");
            if (!StringUtil.isEmpty(plazaId))
                vmVehicleForm.setPlazaId(Long.valueOf(request.getParameter("plazaId")));
            //产品名称
            String plazaName = request.getParameter("plazaName");
            if (!StringUtil.isEmpty(plazaName))
                vmVehicleForm.setPlazaName(request.getParameter("plazaName"));

            //注册时间
            String shiftDate = request.getParameter("shiftDate");
            if (!StringUtil.isEmpty(shiftDate)) {
                vmVehicleForm.setShiftDate(DateUtil.fromDateStringToDate(shiftDate));

                Date nowTime = DateUtil.parseDate(DateUtil.formatDate(DateUtil.fromDateStringToDate(shiftDate), "HH:mm:ss"), "HH:mm:ss");
                List<SysConfig> sysConfigList = vmVehicleService.findSysConfig("teamTime");
                for (int i = 0; i < sysConfigList.size(); i++) {
                    Date startTime = DateUtil.parseDate(sysConfigList.get(i).getStartTime(), "HH:mm:ss");
                    Date endTime = DateUtil.parseDate(sysConfigList.get(i).getEndTime(), "HH:mm:ss");
                    if (DateUtil.belongCalendar(nowTime, startTime, endTime)) {
                        vmVehicleForm.setShiftID(Long.valueOf(sysConfigList.get(i).getCfConfigValue()));
                        vmVehicleForm.setShiftName(sysConfigList.get(i).getCfConfigDescription());
                    }
                }
            }
            vmVehicleForm.setInputTime(new Date());
            vmVehicleForm.setModifyTime(new Date());
            vmVehicleForm.setUpdateSign(0l);
            List<MultipartFile> files = new ArrayList<MultipartFile>();
            if (null != file1 && !file1.isEmpty()) {
                files.add(file1);
            }
            if (null != file2 && !file2.isEmpty()) {
                files.add(file2);
            }
            if (null != file3 && !file3.isEmpty()) {
                files.add(file3);
            }
            if (null != file4 && !file4.isEmpty()) {
                files.add(file4);
            }
            if (null != files) {
                for (MultipartFile file : files) {
                    String imagePath = "";
                    String path = "";
                    if (!file.isEmpty()) {
                   /* 图片规则：   时间_收费站_班次_车牌_部位.JPG
                    5-车卡合一图片，保存到ftp路径下的名称V_VehicleNo+RFID.JPG
                    */
                        String dateStr = DateUtil.formatDate(vmVehicleForm.getShiftDate(), "yyyy-MM-dd");
                        String basePath = "";
                        basePath += users.getSysPlaza().getPlazaName() + "/" + dateStr + "/"; // 路径   收费站/时间

                        basePath = basePath + vmVehicleForm.getVehicleNo() + "_" + vmVehicleForm.getPlateNo() + "/";

                        String contentType = file.getContentType();
                        //获得文件名称
                        String fileName = file.getOriginalFilename();

                        String imageName = DateUtil.formatDate(vmVehicleForm.getShiftDate(), "yyyyMMddHHmmss") + "_" + users.getSysPlaza().getPlazaName() + "_" + vmVehicleForm.getShiftName() + "_" + vmVehicleForm.getPlateNo() + "_" + vmVehicleForm.getVehicleNo() + "_" + fileName;

                        vmVehicleForm.setImageDirectory(image_file_name + basePath + DateUtil.formatDate(vmVehicleForm.getShiftDate(), "yyyyMMddHHmmss") + "_" + users.getSysPlaza().getPlazaName() + "_" + vmVehicleForm.getShiftName() + "_" + vmVehicleForm.getPlateNo() + "_" + vmVehicleForm.getVehicleNo() + "_");

                        //图片存放路径
                        imagePath = base_path + image_file_name + basePath;

                        //图片完整路径
                        path = imagePath + imageName;
                        // imagePath = imageName;
                        newFolder(imagePath);
                        //存放图片
                        file.transferTo(new File(path));

                    }
                    LOGGER.info(" image save path {}", path);
                }
            }
            VmVehicle save = vmVehicleService.save(vmVehicleForm, users);
            return WholeResponse.successResponse("保存数据成功");
        } catch (Exception e) {
            LOGGER.error("saveVmVehicle保存出错" + e.getMessage());
            return WholeResponse.errorResponse("500", "系统异常");
        }

    }


    /**
     * 车辆信息列表
     *
     * @param queryParam
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryvehiclepage", method = RequestMethod.POST)
    public PageUtils queryVehiclePage(VmVehicleQueryParam queryParam, @RequestParam(required = false, defaultValue = "1") int pageNo, @RequestParam(required = false, defaultValue = "10") int pageSize) {
        try {
            Users users = usersService.findById(queryParam.getUserId());
            Page<VmVehicle> vmVehicles = vmVehicleService.listAndroid(queryParam, users, pageNo, pageSize);
            PageUtils pageUtils = new PageUtils(vmVehicles.getContent(), Integer.valueOf(String.valueOf(vmVehicles.getTotalElements())));
            return pageUtils;
        } catch (Exception e) {
            LOGGER.info("queryvehiclepage查询出错" + e.getMessage());
            return null;
        }

    }


    /**
     * 车辆信息
     *
     * @param vehicleNo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryvehicle", method = RequestMethod.POST)
    private VmVehicle queryVehicleRegiste(String vehicleNo) {
        try {
            VmVehicle vmVehicle = vmVehicleService.findById(vehicleNo);
            return vmVehicle;
        } catch (Exception e) {
            LOGGER.info("queryvehicle查询出错" + e.getMessage());
            return null;
        }
    }


    @RequestMapping(value = "/vehicleStatistical")
    @ResponseBody
    public List<StatisticalReportVo> vehicleStatistical(AndroidVehicleParam vehicleParam, Long userId, HttpServletRequest request, HttpServletResponse response) {
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
            List<StatisticalReportVo> vmVehicles = vmVehicleService.AndroidStatisticalVehicle(vehicleParam, sysBaseInformations, users);
            return vmVehicles;
        } catch (Exception e) {
            LOGGER.info("vehicleStatistical查询出错" + e.getMessage());
            return null;
        }
    }


    public static void newFolder(String folderPath) {
        try {
            File myFilePath = new File(folderPath);
            if (!myFilePath.exists()) {
                //创建多级文件夹
                myFilePath.mkdirs();
                System.out.println("创建文件夹路径：" + folderPath);
            }
        } catch (Exception e) {
            System.out.println("新建文件夹操作出错");
            e.printStackTrace();
        }
    }

}
