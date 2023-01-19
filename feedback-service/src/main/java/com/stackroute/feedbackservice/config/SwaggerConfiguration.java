package com.stackroute.feedbackservice.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.function.Predicate;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select().apis(Predicate.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .build();
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "Ease My Stay : User Feedback Service",
                "This Feedback service is used to add the user feedback based on there experience so that it helps other to know about the hotel & there services",
                "v1.0.0",
                "Terms of Service",
                new Contact(
                        "Vipin Shrivastava",
                        "https://www.linkedin.com/in/vipin-shrivastava/",
                        "vipin.shrivastava@globallogic.com"
                ),
                "License of APIS",
                "API license URL",
                Collections.emptyList()
        );
    }
}
