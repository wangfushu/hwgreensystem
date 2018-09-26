package com.xmrbi.hwgreensystem.dao;


import com.xmrbi.hwgreensystem.domain.db.SysConfig;
import com.xmrbi.hwgreensystem.domain.db.SysConfigPK;

import java.util.List;

/**
 * Created by Administrator on 2015/9/21.
 */
public interface SysConfigDao extends BaseDao<SysConfig, SysConfigPK> {

    List<SysConfig> findByCfConfigNameOrderByCfConfigValueAsc(String configName);
}
