package com.xmrbi.hwgreensystem.domain.db;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by wangfs on 2017-09-05 helloword.
 */


public class SysConfigPK implements java.io.Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CF_CONFIGNAME")
    private String cfConfigName;

    @Id
    @Column(name = "CF_CONFIGVALUE")
    private String cfConfigValue;

    public SysConfigPK() {
    }

    public SysConfigPK(String cfConfigName, String cfConfigValue) {
        this.cfConfigName = cfConfigName;
        this.cfConfigValue = cfConfigValue;
    }
}
