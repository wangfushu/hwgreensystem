package com.xmrbi.hwgreensystem.domain.db;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by wangfs on 2017-09-05 helloword.
 */

@Entity
@Table(name = "SYS_CONFIG")
@SuppressWarnings("serial")
@IdClass(SysConfigPK.class)
public class SysConfig {

    @Id
    @Column(name = "CF_CONFIGNAME")
    private String cfConfigName;

    @Id
    @Column(name = "CF_CONFIGVALUE")
    private String cfConfigValue;

    @Column(name = "CF_CONFIGDESCRIPTION")
    private String cfConfigDescription;


    @Column(name = "CF_UPDATETIME")
    private Date cfUpdateTime;

    @Column(name = "starttime")
    private String startTime;

    @Column(name = "endtime")
    private String endTime;

    public String getCfConfigName() {
        return cfConfigName;
    }

    public void setCfConfigName(String cfConfigName) {
        this.cfConfigName = cfConfigName;
    }

    public String getCfConfigValue() {
        return cfConfigValue;
    }

    public void setCfConfigValue(String cfConfigValue) {
        this.cfConfigValue = cfConfigValue;
    }

    public String getCfConfigDescription() {
        return cfConfigDescription;
    }

    public void setCfConfigDescription(String cfConfigDescription) {
        this.cfConfigDescription = cfConfigDescription;
    }


    public Date getCfUpdateTime() {
        return cfUpdateTime;
    }

    public void setCfUpdateTime(Date cfUpdateTime) {
        this.cfUpdateTime = cfUpdateTime;
    }


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SysConfig{");
        sb.append("cfConfigName='").append(cfConfigName).append('\'');
        sb.append(", cfConfigValue='").append(cfConfigValue).append('\'');
        sb.append(", cfConfigDescription='").append(cfConfigDescription).append('\'');
        sb.append(", cfUpdateTime='").append(cfUpdateTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
