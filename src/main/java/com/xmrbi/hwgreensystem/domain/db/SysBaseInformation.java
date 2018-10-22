package com.xmrbi.hwgreensystem.domain.db;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wangfs on 2017-09-05 helloword.
 */

@Entity
@Table(name = "SYS_BASEINFORMATION")
public class SysBaseInformation {
    protected Long id;


    private String biType;
    private String biTypeName;

    private String biTypeId;


    private String biValue;


    private String biDescription;


    private Date biModifyTime;


    private String biUserNo;


    private String biUserName;


    private String biUserId;


    private String aMemo;//备注

    private Long sort;

    @Id
    @TableGenerator(name = "ID_GENERATOR",
            table = "sys_seq_table",
            pkColumnName = "ST_SeqName",
            pkColumnValue = "SysBaseInformation.id",
            valueColumnName = "ST_SeqValue",
            allocationSize = 1,
            initialValue = 7
    )
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ID_GENERATOR")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SORT")
    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    @Column(name = "BI_TYPE")
    public String getBiType() {
        return biType;
    }

    public void setBiType(String biType) {
        this.biType = biType;
    }

    @Column(name = "BI_VALUE")
    public String getBiValue() {
        return biValue;
    }

    public void setBiValue(String biValue) {
        this.biValue = biValue;
    }

    @Column(name = "BI_DESCRIPTION")
    public String getBiDescription() {
        return biDescription;
    }

    public void setBiDescription(String biDescription) {
        this.biDescription = biDescription;
    }


    @Column(name = "BI_MODIFYTIME")
    public Date getBiModifyTime() {
        return biModifyTime;
    }

    public void setBiModifyTime(Date biModifyTime) {
        this.biModifyTime = biModifyTime;
    }

    @Column(name = "BI_USERNO")
    public String getBiUserNo() {
        return biUserNo;
    }

    public void setBiUserNo(String biUserNo) {
        this.biUserNo = biUserNo;
    }

    @Column(name = "BI_USERNAME")
    public String getBiUserName() {
        return biUserName;
    }

    public void setBiUserName(String biUserName) {
        this.biUserName = biUserName;
    }

    @Column(name = "BI_USERID")
    public String getBiUserId() {
        return biUserId;
    }

    public void setBiUserId(String biUserId) {
        this.biUserId = biUserId;
    }

    @Column(name = "AMEMO")
    public String getaMemo() {
        return aMemo;
    }

    public void setaMemo(String aMemo) {
        this.aMemo = aMemo;
    }

    @Column(name = "BI_TYPEID")
    public String getBiTypeId() {
        return biTypeId;
    }

    public void setBiTypeId(String biTypeId) {
        this.biTypeId = biTypeId;
    }

    @Column(name = "BI_TYPENAME")
    public String getBiTypeName() {
        return biTypeName;
    }

    public void setBiTypeName(String biTypeName) {
        this.biTypeName = biTypeName;
    }
}
