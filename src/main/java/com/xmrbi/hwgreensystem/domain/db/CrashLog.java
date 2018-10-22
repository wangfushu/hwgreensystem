package com.xmrbi.hwgreensystem.domain.db;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CRASHLOG")
@DynamicInsert
public class CrashLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CRASHID")
    private Long crashId;

    @Column(name = "CRASHTEXT")
    private String crashText;

    @Column(name = "CRASHTIME")
    private Date crashTime;


    public Long getCrashId() {
        return crashId;
    }

    public void setCrashId(Long crashId) {
        this.crashId = crashId;
    }

    public String getCrashText() {
        return crashText;
    }

    public void setCrashText(String crashText) {
        this.crashText = crashText;
    }

    public Date getCrashTime() {
        return crashTime;
    }

    public void setCrashTime(Date crashTime) {
        this.crashTime = crashTime;
    }
}
