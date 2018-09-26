package com.xmrbi.hwgreensystem.domain.param;


import com.xmrbi.hwgreensystem.until.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

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
 * @Date: 2018-09-19 10:43
 */
public class DataTableParam {
    private int offset;
    private Integer page;
    private int limit;
    private String timefrom;
    private String timeto;
    private Date timetoFormat;
    private Date timefromFormat;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getPage() {
        return offset / limit + 1;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getTimefrom() {
        return timefrom;
    }

    public void setTimefrom(String timefrom) {
        this.timefrom = timefrom;
    }

    public String getTimeto() {
        return timeto;
    }

    public void setTimeto(String timeto) {
        this.timeto = timeto;
    }

    public Date getTimetoFormat() {
        if (StringUtils.isNotBlank(this.timeto) && this.timetoFormat == null) {
            this.timetoFormat = DateUtil.parseDate(this.timeto, "yyyy-MM-dd");
        }
        return timetoFormat;
    }

    public void setTimetoFormat(Date timetoFormat) {
        this.timetoFormat = timetoFormat;
    }

    public Date getTimefromFormat() {
        if (StringUtils.isNotBlank(this.timefrom) && this.timefromFormat == null) {
            this.timefromFormat = DateUtil.parseDate(this.timefrom, "yyyy-MM-dd");
        }
        return timefromFormat;
    }

    public void setTimefromFormat(Date timefromFormat) {
        this.timefromFormat = timefromFormat;
    }
}
