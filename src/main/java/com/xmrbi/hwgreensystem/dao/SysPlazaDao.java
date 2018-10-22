package com.xmrbi.hwgreensystem.dao;


import com.xmrbi.hwgreensystem.domain.db.SysPlaza;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2015/9/21.
 */
public interface SysPlazaDao extends BaseDao<SysPlaza, Long> {
	SysPlaza findByPlazaId(Long plazaId);

	@Query(value = "with cte as ( select * from sys_plaza where parentId=? " +
			"union all " +
			"select  a.* from sys_plaza as a,cte as b where a.parentId=b.plazaId " +
			")" +
			" select cte.plazaId from cte",nativeQuery = true)
	List<Long> findPlazaIdByParentId(Long parentId);

	@Query(value = "with cte as ( select * from sys_plaza where parentId=? " +
			"union all " +
			"select  a.* from sys_plaza as a,cte as b where a.parentId=b.plazaId " +
			")" +
			" select cte.* from cte where cte.level=?",nativeQuery = true)
	List<SysPlaza> findSysPlazasByParentIdAAndLevel(Long parentId,Long level);


	@Query(value = "with cte as ( select * from sys_plaza where parentId=? " +
			"union all " +
			"select  a.* from sys_plaza as a,cte as b where a.parentId=b.plazaId " +
			")" +
			" select cte.plazaId from cte where cte.level=?",nativeQuery = true)
	List<Long> findPlazaIdByParentIdAAndLevel(Long parentId,Long level);
}
