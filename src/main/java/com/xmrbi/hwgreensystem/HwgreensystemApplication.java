package com.xmrbi.hwgreensystem;

import com.xmrbi.hwgreensystem.dao.util.IdUtilDao;
import com.xmrbi.hwgreensystem.service.IdPool;
import com.xmrbi.hwgreensystem.service.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
public class HwgreensystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HwgreensystemApplication.class, args);
    }

/*    @Bean
    public IdUtilDao idUtilDao(){
        return new IdUtilDao();
    }
    @Bean
    public SpringContextUtil springContextUtil(){
        return new SpringContextUtil();
    }*/
}
