package com.stackroute.userprofileservice.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.Collections;
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
                "Ease My Stay : User Profile Service",
                "This service creates private APIs for registration of users and allows CRUD operations.",
                "v1.0.0",
                "Terms of Service",
                new Contact(
                        "Swastika Shanker",
                        "https://www.linkedin.com/in/swastika-shanker-47b5a0171/",
                        "swastika.shanker@globallogic.com"
                ),
                "License of APIS",
                "API license URL",
                Collections.emptyList()
        );
    }


}


//http://localhost:9001/swagger-ui/index.html#/