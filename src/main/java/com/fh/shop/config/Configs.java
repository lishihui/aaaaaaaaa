package com.fh.shop.config;


import com.fh.shop.interceptor.ApiIdempotent;
import com.fh.shop.interceptor.MyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class Configs extends WebMvcConfigurerAdapter {
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        /* compiled code */
        registry.addInterceptor(myInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(apiIdempotent()).addPathPatterns("/**");
    }

    public void addArgumentResolvers(java.util.List<org.springframework.web.method.support.HandlerMethodArgumentResolver> argumentResolvers) {
        /* compiled code */
        argumentResolvers.add(cartArgumentResolver());
    }

    /*拦截器*/
    @Bean
    public MyInterceptor myInterceptor() {
        return new MyInterceptor();
    }

    /*密等性拦截器*/
    @Bean
    public ApiIdempotent apiIdempotent() {
        return new ApiIdempotent();
    }

    /*参数解析器*/
    @Bean
    public CartArgumentResolver cartArgumentResolver() {
        return new CartArgumentResolver();
    }

}
