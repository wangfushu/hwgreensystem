package com.xmrbi.hwgreensystem.domain.db;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by yangjb on 2017/6/28.
 * helloWorld
 */
@Entity
@Table(name = "users")
public class Users {
    protected Long id;
    private String userNo;
    private String password;
    private String telphone;
    private String userName;
    private Date gmtCreate;
    private Date gmtModify;
    private Integer status;
    private String remark;
    private List<Role> roles;
    private List<Role> rolesNoLazy;
    private SysPlaza sysPlaza;

    private String iDCard;
    private String fax;
    private String address;
    private String zip;
    private String email;



    @Id
    @TableGenerator(name="ID_GENERATOR",
            table="sys_seq_table",
            pkColumnName="ST_SeqName",
            pkColumnValue="Users.id",
            valueColumnName="ST_SeqValue",
            allocationSize=1,
            initialValue=1
    )
    @GeneratedValue(strategy=GenerationType.TABLE, generator="ID_GENERATOR")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "USERNO")
    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @JsonBackReference
    @OneToMany(targetEntity = Role.class,fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id",
            referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "role_id",
            referencedColumnName = "id")})
    @Cascade(value = {CascadeType.LOCK})
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Transient
    public List<Role> getRolesNoLazy() {
        return rolesNoLazy;
    }

    public void setRolesNoLazy(List<Role> rolesNoLazy) {
        this.rolesNoLazy = rolesNoLazy;
    }

    @Transient
    public boolean getIsInspector() {
        return isInspector();
    }


    @Column(name = "USERNAME")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }






    @Column(name = "gmt_create")
    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Column(name = "gmt_modify")
    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    @Transient
    public long getRoleId() {
        if (CollectionUtils.isEmpty(this.roles)) {
            return -1;
        }
        return this.roles.get(0).getId();
    }

    @Transient
    public String getRoleName() {
        if (CollectionUtils.isEmpty(this.roles)) {
            return "";
        }
        return this.roles.get(0).getRemark();
    }


    //是否是超级管理员
    @Transient
    public boolean isSuperAdmin() {
        List<Role> roles = this.roles;
        if (this.roles == null) {
            return false;
        }
        for (Role role : roles) {
            if (StringUtils.equalsIgnoreCase(role.getName(), "ROLE_SUPERADMIN")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否 稽查员
     * @return
     */
    @Transient
    public boolean isInspector() {
        List<Role> roles = this.roles;
        if (this.roles == null) {
            return false;
        }
        for (Role role : roles) {
            if (StringUtils.equalsIgnoreCase(role.getName(), "ROLE_USER")||StringUtils.equalsIgnoreCase(role.getName(), "ROLE_SITE_ADMIN")) {
                return true;
            }
        }
        return false;
    }


    @Transient
    public boolean getIsUser() {
        List<Role> roles = this.roles;
        for (Role role : roles) {
            if (StringUtils.equalsIgnoreCase(role.getName(), "ROLE_USER")) {
                return true;
            }
        }
        return false;
    }


    @ManyToOne(targetEntity = SysPlaza.class)
    @JoinColumn(name = "PLAZANO", updatable = true)
    public SysPlaza getSysPlaza() {
        return sysPlaza;
    }

    public void setSysPlaza(SysPlaza sysPlaza) {
        this.sysPlaza = sysPlaza;
    }
    @Column(name = "IDCARD")
    public String getiDCard() {
        return iDCard;
    }

    public void setiDCard(String iDCard) {
        this.iDCard = iDCard;
    }
    @Column(name = "FAX")
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    @Column(name = "ZIP")
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }




    @Override
    public String toString() {
        return "Users{" +
                "userNo='" + userNo + '\'' +
                ", password='" + password + '\'' +
                ", telphone='" + telphone + '\'' +
                ", userName='" + userName + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModify=" + gmtModify +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", rolesNoLazy=" + rolesNoLazy +
                ", sysPlaza=" + sysPlaza.getPlazaId() +
                ", iDCard='" + iDCard + '\'' +
                ", fax='" + fax + '\'' +
                ", address='" + address + '\'' +
                ", zip='" + zip + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    /*
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Users{");
        sb.append("userNo='").append(userNo).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", telphone='").append(telphone).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModify=").append(gmtModify);
        sb.append(", lastLoginTime=").append(lastLoginTime);
        sb.append(", status=").append(status);
        sb.append(", remark='").append(remark).append('\'');
        sb.append('}');
        return sb.toString();
    }
*/

  /*  public static int getRandomNum(int min, int max) {

        Random random = new Random();

        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    public static int ss(int areaId) {
        int randomNum = getRandomNum(1, 10);

        //90%投大小单双
        int biliId = 0;
        randomNum = getRandomNum(0, 10);
        if (randomNum >= 1) {
            //投注极大极小的概率降低
            biliId = getRandomNum(1, 10);
            if (biliId % 5 == 0) {
                if (getRandomNum(1, 10) > 3) {
                    biliId++;
                }
            }
        } else {
            //30%投数字
            biliId = getRandomNum(11, 38);
        }

        biliId = biliId + (areaId - 1) * 42;
        return biliId;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(getRandomNum(1, 10));
        }
    }*/
}
