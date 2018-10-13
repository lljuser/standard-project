package com.heyi.webapp.blockchain.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     *  跨域处理
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController( "/" ).setViewName( "redirect:swagger-ui.html" );
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping( "/**" )
                .allowedHeaders( "*" )
                .allowedMethods( "GET", "POST" )
                .allowedOrigins( "*" )
                .allowCredentials(true);
    }

}
