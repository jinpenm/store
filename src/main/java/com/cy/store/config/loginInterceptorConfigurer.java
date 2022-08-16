package com.cy.store.config;

import com.cy.store.interceptor.loginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class loginInterceptorConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        HandlerInterceptor interceptor = new loginInterceptor();

        List<String> patterns = new ArrayList<>();
        patterns.add("/bootstrap3/**");
        patterns.add("/css/**");
        patterns.add("/images/**");
        patterns.add("/js/**");
        patterns.add("/web/login.html");
        patterns.add("/web/register.html");
        patterns.add("/web/product.html");
        patterns.add("/web/index.html");
        patterns.add("/users/reg");
        patterns.add("/users/login");

        registry.addInterceptor(interceptor)//注册拦截器
                .addPathPatterns("/**")//要拦截的目录
                .excludePathPatterns(patterns);//在拦截目录中的白名单 参数是list集合



    }
}
