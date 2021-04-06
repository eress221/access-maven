package com.eress.bootmaven.config;

import com.eress.bootmaven.common.CrossScriptingFilter;
import com.eress.bootmaven.common.Interceptor;
import org.apache.catalina.filters.HttpHeaderSecurityFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Interceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/css/*", "/fonts/**", "/img/*", "/js/*", "/vendor/**", "/error")
                .excludePathPatterns("/login/*");
    }

    @Bean
    public FilterRegistrationBean<Filter> getFilterRegistrationBean() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CrossScriptingFilter());
        registrationBean.setOrder(1);
        registrationBean.addUrlPatterns("/**");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<Filter> httpHeaderSecurityFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        HttpHeaderSecurityFilter httpHeaderSecurityFilter = new HttpHeaderSecurityFilter();
        httpHeaderSecurityFilter.setXssProtectionEnabled(true);
        registrationBean.setFilter(httpHeaderSecurityFilter);
        return registrationBean;
    }
}
