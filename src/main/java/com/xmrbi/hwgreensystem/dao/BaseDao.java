package com.xmrbi.hwgreensystem.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/9/21.
 */

@NoRepositoryBean
public interface BaseDao<T, PK extends Serializable>
		extends PagingAndSortingRepository<T, PK>, JpaSpecificationExecutor<T> {

}
