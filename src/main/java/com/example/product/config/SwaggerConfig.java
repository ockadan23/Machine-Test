package com.example.product.config;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Product Sales API")
                        .version("1.0")
                        .description("API for Product and Sales Management"))
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"));
    }

    @Bean
    public OpenApiCustomizer operationCustomizer() {
        return openApi -> {
            openApi.getPaths().values().forEach(pathItem -> {
                setSecurityForOperations(pathItem.getGet(), "user");
                setSecurityForOperations(pathItem.getPost(), "user");
                setSecurityForOperations(pathItem.getPut(), "admin");
                setSecurityForOperations(pathItem.getDelete(), "admin");
                setSecurityForOperations(pathItem.getPatch(), "admin");
            });
        };
    }

    private void setSecurityForOperations(Operation operation, String role) {
        if (operation != null) {
            operation.setSecurity(Collections.singletonList(
                    new SecurityRequirement().addList("basicAuth", Collections.singletonList(role))
            ));
        }
    }
}