package com.example.product.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RoleBasedOpenApiCustomizer implements OpenApiCustomizer {

    private static final List<String> PUBLIC_METHODS = Arrays.asList("GET", "POST");

    @Override
    public void customise(OpenAPI openApi) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication != null && authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equals("ROLE_ADMIN"));

        if (!isAdmin) {
            openApi.getPaths().values().forEach(pathItem -> {
                if (!PUBLIC_METHODS.contains("PUT")) pathItem.setPut(null);
                if (!PUBLIC_METHODS.contains("DELETE")) pathItem.setDelete(null);
                if (!PUBLIC_METHODS.contains("PATCH")) pathItem.setPatch(null);
            });
        }
    }
}