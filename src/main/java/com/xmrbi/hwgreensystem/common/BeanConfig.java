package com.xmrbi.hwgreensystem.common;

import com.xmrbi.hwgreensystem.dao.util.IdUtilDao;
import com.xmrbi.hwgreensystem.service.IdPool;
import com.xmrbi.hwgreensystem.service.SpringContextUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
 * @Date: 2018-09-27 9:42
 */
@Configuration
public class BeanConfig {

    @Bean
    public IdUtilDao getIdUtilDao(){
        return new IdUtilDao();
    }
    @Bean
    public SpringContextUtil getSpringContextUtil(){
        return new SpringContextUtil();
    }

    @Bean
    public  IdPool getIdPool(){
        IdPool idPool=new IdPool();
        idPool.setIdUtilDao(getIdUtilDao());
        idPool.setSpringContextUtil(getSpringContextUtil());
        return idPool;
    }

}
