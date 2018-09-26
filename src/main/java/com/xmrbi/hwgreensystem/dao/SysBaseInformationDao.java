package com.xmrbi.hwgreensystem.dao;


import com.xmrbi.hwgreensystem.domain.db.SysBaseInformation;
import com.xmrbi.hwgreensystem.domain.vo.TreeVo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2015/9/21.
 */
public interface SysBaseInformationDao extends BaseDao<SysBaseInformation, Long> {

    @Query(value = "select distinct Bi_Type as name,Bi_TypeId as kid  from sys_baseinformation ",nativeQuery = true)
    List<TreeVo> getAllType();

	/*@Query(value = "from SysBaseInformation cr where biType = ? order by sort desc")
    List<SysBaseInformation> querybybiType(String biType);*/
}
