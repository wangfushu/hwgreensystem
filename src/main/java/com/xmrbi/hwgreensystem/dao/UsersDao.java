package com.xmrbi.hwgreensystem.dao;




import com.xmrbi.hwgreensystem.domain.db.SysPlaza;
import com.xmrbi.hwgreensystem.domain.db.Users;

import java.util.List;
import java.util.Optional;

/**
 * Created by yangjb on 2017/9/21.
 * hello
 */
public interface UsersDao extends BaseDao<Users, Long> {
    Users findByUserNo(String userNo);

    Optional<Users> findById(Long id);

    List<Users> findBySysPlaza(SysPlaza sysPlaza);
    List<Users> findByTelphone(String telphone);


}
