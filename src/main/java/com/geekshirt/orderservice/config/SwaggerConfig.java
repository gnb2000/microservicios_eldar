package com.geekshirt.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
@Import({BeanValidatorPluginsConfiguration.class})
public class SwaggerConfig {

    @Bean //Si no pongo esta etiqueta, no es administrada por el spring container y no me puedo hacerle autowired
    public Docket apiDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any()) //Todos los RestControllers
                //.paths(PathSelectors.any()) //Todos los servicios
                .build().apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo(){
        return new ApiInfo("GeekShirt Order Service API",
                            "This API provides all methods required for order management",
                            "1.0",
                            "TERMS OF SERVICE URL",
                            new Contact("Gonza","http://www.google.com","gonza@gmail.com"),
                            "LICENSE",
                            "LICENSE URL",Collections.emptyList());
    }
}
