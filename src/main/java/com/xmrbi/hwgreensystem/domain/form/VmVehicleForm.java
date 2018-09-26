package com.xmrbi.hwgreensystem.domain.form;

import java.util.Date;

/**
 * Created by wangfs on 2017-10-19 helloword.
 */
public class VmVehicleForm {
    private String vehicleNo;//车辆编号生成规则：5位网点号  + 8位日期(YYYYMMDD) + 7位流水号;
    private String plateNo;//车牌
    private String plateColor;//车牌颜色
    private Long type;//车型
    private Float capacity;//载量
    private Float calCapacity;//实际载量
    private Long peccNum;//违章次数
    private Double freeFee;//免费金额
    private Double realFee;//免费金额
    private String customerName;//车主姓名
    private String transactorPhone;//用户手机
    private String remark;//备注
    private String imageDirectory;//完整图片路径
    private String condition;//不符合事项
    private Long shiftID;//班组
    private Date shiftDate;//开单时间(出口时间)

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

    public Float getCapacity() {
        return capacity;
    }

    public void setCapacity(Float capacity) {
        this.capacity = capacity;
    }

    public Float getCalCapacity() {
        return calCapacity;
    }

    public void setCalCapacity(Float calCapacity) {
        this.calCapacity = calCapacity;
    }

    public Long getPeccNum() {
        return peccNum;
    }

    public void setPeccNum(Long peccNum) {
        this.peccNum = peccNum;
    }

    public Double getFreeFee() {
        return freeFee;
    }

    public void setFreeFee(Double freeFee) {
        this.freeFee = freeFee;
    }

    public Double getRealFee() {
        return realFee;
    }

    public void setRealFee(Double realFee) {
        this.realFee = realFee;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTransactorPhone() {
        return transactorPhone;
    }

    public void setTransactorPhone(String transactorPhone) {
        this.transactorPhone = transactorPhone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImageDirectory() {
        return imageDirectory;
    }

    public void setImageDirectory(String imageDirectory) {
        this.imageDirectory = imageDirectory;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Long getShiftID() {
        return shiftID;
    }

    public void setShiftID(Long shiftID) {
        this.shiftID = shiftID;
    }

    public Date getShiftDate() {
        return shiftDate;
    }

    public void setShiftDate(Date shiftDate) {
        this.shiftDate = shiftDate;
    }
}
