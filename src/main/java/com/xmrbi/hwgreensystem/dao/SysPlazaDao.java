package com.xmrbi.hwgreensystem.dao;


import com.xmrbi.hwgreensystem.domain.db.SysPlaza;

/**
 * Created by Administrator on 2015/9/21.
 */
public interface SysPlazaDao extends BaseDao<SysPlaza, Long> {
	SysPlaza findByPlazaId(Long plazaId);
}
