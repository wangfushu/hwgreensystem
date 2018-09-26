package com.xmrbi.hwgreensystem.domain.param;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by wangfs on 2017/7/2. helloWorld
 */
public class VmVehicleQueryParam {
    private String vehicleNo;//车辆编号生成规则：5位网点号  + 8位日期(YYYYMMDD) + 7位流水号;
    private String plateNo;//车牌
    private String plateColor;//车牌颜色
    private Long type;//车型
    private Long shiftID;//班组
    private String startPassTime;
    private String endPassTime;
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getShiftID() {
        return shiftID;
    }

    public void setShiftID(Long shiftID) {
        this.shiftID = shiftID;
    }

    public String getStartPassTime() {
        return startPassTime;
    }

    public void setStartPassTime(String startPassTime) {
        this.startPassTime = startPassTime;
    }

    public String getEndPassTime() {
        return endPassTime;
    }

    public void setEndPassTime(String endPassTime) {
        this.endPassTime = endPassTime;
    }
}
