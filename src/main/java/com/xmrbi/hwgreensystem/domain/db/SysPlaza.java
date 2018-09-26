package com.xmrbi.hwgreensystem.domain.db;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2017-07-31.
 */

@Entity
@Table(name = "SYS_PLAZA")
public class SysPlaza {

    @Id
    @TableGenerator(name="ID_GENERATOR",
            table="sys_seq_table",
            pkColumnName="ST_SeqName",
            pkColumnValue="SysPlaza.plazaId",
            valueColumnName="ST_SeqValue",
            allocationSize=1,
            initialValue=6
    )
    @GeneratedValue(strategy=GenerationType.TABLE, generator="ID_GENERATOR")
    @Column(name = "plazaid")
    private Long plazaId;

    @Column(name = "parentid")
    private Long parentId;

    @Column(name = "plazaname")
    private String plazaName;


    @Column(name = "plazaremark")
    private String plazaRemark;

    @Column(name = "ordernum")
    private Integer orderNum;

    @Column(name = "delflag")
    private Integer delFlag;

    public Long getPlazaId() {
        return plazaId;
    }

    public void setPlazaId(Long plazaId) {
        this.plazaId = plazaId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getPlazaName() {
        return plazaName;
    }

    public void setPlazaName(String plazaName) {
        this.plazaName = plazaName;
    }

    public String getPlazaRemark() {
        return plazaRemark;
    }

    public void setPlazaRemark(String plazaRemark) {
        this.plazaRemark = plazaRemark;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    //是否是所有网点
    @Transient
    public boolean isAllPlazaNo() {
          if (plazaId.equals(0l)) {
                return true;
          }
        return false;
    }

    @Override
    public String toString() {
        return "SysPlaza{" +
                "plazaId=" + plazaId +
                ", parentId=" + parentId +
                ", plazaName='" + plazaName + '\'' +
                ", plazaRemark='" + plazaRemark + '\'' +
                ", orderNum=" + orderNum +
                ", delFlag=" + delFlag +
                '}';
    }
}
