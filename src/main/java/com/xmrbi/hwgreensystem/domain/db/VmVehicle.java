package com.xmrbi.hwgreensystem.domain.db;


import com.xmrbi.hwgreensystem.until.DateUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "vehicle")
public class VmVehicle {

	@Id
	@Column(name = "VEHICLENO")
	private String vehicleNo;//车辆编号生成规则：5位网点号  + 8位日期(YYYYMMDD) + 7位流水号;

	@Column(name = "PLATENO")
	private String plateNo;//车牌

	@Column(name = "PLATECOLOR")
	private String plateColor;//车牌颜色

	@Column(name = "type")
	private Long type;//车型


	@Column(name = "capacity")
	private Float capacity;//载量
	@Column(name = "calcapacity")
	private Float calCapacity;//实际载量


	@Column(name = "peccnum")
	private Long peccNum;//违章次数

	@Column(name = "freefee")
	private Double freeFee;//免费金额

	@Column(name = "realfee")
	private Double realFee;//免费金额


	@Column(name = "customername")
	private String customerName;//车主姓名

	@Column(name = "transactorphone")
	private String transactorPhone;//用户手机

	@Column(name = "remark")
	private String remark;//备注

	@Column(name = "imagedirectory")
	private String imageDirectory;//完整图片路径

	@Column(name = "condition")
	private String condition;//不符合事项


	@Column(name = "shiftid")
	private Long shiftID;//班组

	@Column(name = "shiftdate")
	private Date shiftDate;//开单时间

	@Column(name = "USERNO")
	private String userNo;
	@Column(name = "userid")
	private Long userId;
	@Column(name = "USERNAME")
	private String userName;

	@Column(name = "UPDATESIGN")
	private Long updateSign;
	@Column(name = "productname")
	private String productName;//产品名称
	@Column(name = "plazaid")
	private Long plazaId;//出站口Id
	@Column(name = "plazaname")
	private String plazaName;//出站口名称

    @Column(name = "inputtime")
    private Date inputTime;//开单时间
    @Column(name = "modifytime")
    private Date modifyTime;//更新时间



	@Transient
	private String shiftName;//班组名称

	@Transient
	private String shiftDateStr;//开单时间字符串

	@Transient
	private String typeName;//车辆类型

	public String getTypeName() {
		return typeName;
	}

    public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getShiftName() {
		return shiftName;
	}

	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
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

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getUpdateSign() {
		return updateSign;
	}

	public void setUpdateSign(Long updateSign) {
		this.updateSign = updateSign;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getPlazaId() {
		return plazaId;
	}

	public void setPlazaId(Long plazaId) {
		this.plazaId = plazaId;
	}

	public String getPlazaName() {
		return plazaName;
	}

	public void setPlazaName(String plazaName) {
		this.plazaName = plazaName;
	}


	public String getShiftDateStr() {
		return DateUtil.formatDate(this.shiftDate,"yyyy-MM-dd HH:mm:ss");
	}

	public void setShiftDateStr(String shiftDateStr) {
		this.shiftDateStr = shiftDateStr;
	}

	@Override
	public String toString() {
		return "VmVehicle{" +
				"vehicleNo='" + vehicleNo + '\'' +
				", plateNo='" + plateNo + '\'' +
				", plateColor='" + plateColor + '\'' +
				", type=" + type +
				", capacity=" + capacity +
				", calCapacity=" + calCapacity +
				", peccNum=" + peccNum +
				", freeFee=" + freeFee +
				", realFee=" + realFee +
				", customerName='" + customerName + '\'' +
				", transactorPhone='" + transactorPhone + '\'' +
				", remark='" + remark + '\'' +
				", imageDirectory='" + imageDirectory + '\'' +
				", condition='" + condition + '\'' +
				", shiftID=" + shiftID +
				", shiftDate=" + shiftDate +
				", userNo='" + userNo + '\'' +
				", userName='" + userName + '\'' +
				", updateSign=" + updateSign +
				", shiftName='" + shiftName + '\'' +
				'}';
	}
}
