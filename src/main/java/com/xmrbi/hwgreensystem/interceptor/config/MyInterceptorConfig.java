package com.xmrbi.hwgreensystem.interceptor.config;


import com.xmrbi.hwgreensystem.common.MessageConfigConstant;
import com.xmrbi.hwgreensystem.interceptor.AbsolutelyPathInterceptor;
import com.xmrbi.hwgreensystem.interceptor.CurrentUserInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

///**
// * 自定义拦截器的配置，继承WebMvcConfigurationSupport会导致Spring Boot对mvc的自动配置失效，但可以在前后端分离项目中
// * 如果需要让Spring Boot的自动配置生效，需要重写addResourceHandlers方法，将自动配置的路径放开
// * @author shengwu ni
// * @date 2018/08/03
// */
//@Configuration
//public class MyInterceptorConfig extends WebMvcConfigurationSupport {
//
//    @Override
//    protected void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
//        super.addInterceptors(registry);
//    }
//
//    /**
//     * 用来指定静态资源不被拦截，否则继承WebMvcConfigurationSupport这种方式会导致静态资源无法直接访问
//     * @param registry
//     */
//    @Override
//    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//        super.addResourceHandlers(registry);
//    }
//}

/**
 * 自定义拦截器的配置，实现WebMvcConfigurer不会导致Spring Boot对mvc的自动配置失效，可以用在非前后端分离的项目中
 * @author shengwu ni
 * @date 2018/08/03
 */
@Configuration
public class MyInterceptorConfig implements WebMvcConfigurer {
    @Value("${image_base_path}")
    private String base_path;
    @Value("${image_file_name}")
    private String image_file_name;


    @Bean
    public HandlerInterceptor getCurrentUserInterceptor(){
        return new CurrentUserInterceptor();
    }
    @Bean
    public HandlerInterceptor getAbsolutelyPathInterceptor(){
        return new AbsolutelyPathInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 实现WebMvcConfigurer不会导致静态资源被拦截

        registry.addInterceptor(getAbsolutelyPathInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(getCurrentUserInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/"+image_file_name+"**")
                .addResourceLocations("file:"+base_path+image_file_name+"/");
        registry.addResourceHandler("/update/**").addResourceLocations("classpath:/update_resources/");
    }
}