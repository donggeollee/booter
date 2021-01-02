package com.boot.meal.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * @Created by donggeol92@gmail.com on 2020-12-23
 * @Github : http://github.com/donggeollee
 */
@Slf4j
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api(){
        log.info("SWAGGER_3 config complete");
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any()) // 현재 RequestMapping으로 할당된 모든 URL 리스트를 추출
                .paths(PathSelectors.any()) // 그중 /api/** 인 URL들만 필터링
//                .paths(PathSelectors.ant("/api/**")) // /api/
                .build()
                .apiInfo(this.customApiInfo());
    }

    /**
     * Cust info.
     * @return the api info
     */
    public ApiInfo customApiInfo() {
        return new ApiInfo("Booter",                                              // Title
                "Spring Boot Services",                                      // Description
                "1.0", // Version
                "#", // Terms of Service
                new Contact("e-build", "https://www.notion.so/godofshower/386bfcd168f84cf7b59eaa3639cca189", "donggeol92@gmail.com"), // Contact
                null, // License
                null, Collections.emptyList()
        );
    }
}
