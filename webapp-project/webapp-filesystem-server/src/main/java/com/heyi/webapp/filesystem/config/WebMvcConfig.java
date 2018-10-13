package com.heyi.webapp.filesystem.config;

import com.heyi.webapp.filesystem.interceptor.CookieInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController( "/swagger" ).setViewName( "redirect:index.html" );
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping( "/**" )
                .allowedHeaders( "*" )
                .allowedMethods( "GET", "POST" )
                .allowedOrigins( "*" )
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CookieInterceptor());
    }
}
