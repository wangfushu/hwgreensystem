package com.xmrbi.hwgreensystem.domain.param;

/**
 * code is far away from bug with the animal protecting
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 *
 * @description :
 * ---------------------------------
 * @Author: wangfushu
 * @Date: 2018-09-19 10:47
 */
public class AndroidVehicleParam{
    private String startPassTime;
    private String endPassTime;
    private Long plazaId;

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

    public Long getPlazaId() {
        return plazaId;
    }

    public void setPlazaId(Long plazaId) {
        this.plazaId = plazaId;
    }
}
