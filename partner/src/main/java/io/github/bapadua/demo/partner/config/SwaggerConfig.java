package io.github.bapadua.demo.partner.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author ice_bpadua on 03/10/2020
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    private final static String basePackage = "io.github.bapadua.demo.partner.controllers";

    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    public ApiInfo metaDate() {
        return new ApiInfoBuilder()
                .title("Sócio Torcedor")
                .description("Spring rest Api")
                .version("1.0.0")
                .licenseUrl("https://bapadua.github.io")
                .build();
    }

}
