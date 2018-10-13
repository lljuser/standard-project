package com.heyi.webapp.blockchain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    private static final String SWAGGER_SCAN_BASE_PACKAGE = "com.heyi.webapp";
    private static final String VERSION = "v1.0.0";

    @Value("${swagger.enable}")
    private boolean enableSwagger;

    @Bean
    public Docket createBlockRestApi() {
        return new Docket( DocumentationType.SWAGGER_2 )
                .groupName( "Block" )
                .select()
                .apis( RequestHandlerSelectors.basePackage( SWAGGER_SCAN_BASE_PACKAGE ) )
                .build()
                .apiInfo( apiInfoBlock() )
                .enable( enableSwagger );
    }

    private ApiInfo apiInfoBlock() {
        return new ApiInfoBuilder()
                .title( "Block-server Project API" )
                .termsOfServiceUrl( "http://10.1.3.155:8003" )
                .version( VERSION )
                .build();
    }

    @Bean
    UiConfiguration uiConfig() {
        return new UiConfiguration( null, "list", "alpha", "schema",
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, true, 60000L );
    }
}
