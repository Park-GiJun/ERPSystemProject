package com.gijun.erpproject.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${swagger.default.username}")
    private String defaultUsername;

    @Value("${swagger.default.password}")
    private String defaultPassword;

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        // API 문서 설명
        String description = String.format("""
                SI Company ERP System API Documentation
                
                인증 테스트 계정:
                - Username: %s
                - Password: %s
                
                인증 방법:
                1. /api/auth/login API로 로그인하여 토큰을 발급받습니다.
                2. 우측 상단의 'Authorize' 버튼을 클릭합니다.
                3. 발급받은 토큰을 'Bearer {token}' 형식으로 입력합니다.
                4. 이제 모든 API를 테스트할 수 있습니다.
                
                토큰 관련:
                - 토큰 유효성 검사: /api/auth/validate-token
                - 로그아웃: /api/auth/logout
                """, defaultUsername, defaultPassword);

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt", securityScheme))
                .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"))
                .info(new Info()
                        .title("SI Company ERP API")
                        .description(description)
                        .version("v0.0.1"));
    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            // login API에 대한 예시 요청 추가
            openApi.getPaths().forEach((path, pathItem) -> {
                if (path.equals("/api/auth/login")) {
                    pathItem.readOperations().forEach(operation -> {
                        // 로그인 API 설명 추가
                        operation.setDescription(String.format("""
                            사용자 인증 후 JWT 토큰을 발급합니다.
                            
                            테스트 계정:
                            - Username: %s
                            - Password: %s
                            
                            응답으로 받은 토큰은 다음과 같이 사용하세요:
                            1. 우측 상단의 'Authorize' 버튼 클릭
                            2. 'Bearer {token}' 형식으로 입력
                            """, defaultUsername, defaultPassword));

                        // 요청 본문 예시 추가
                        Example loginExample = new Example()
                                .value(String.format("""
                                    {
                                      "username": "%s",
                                      "password": "%s"
                                    }
                                    """, defaultUsername, defaultPassword));

                        MediaType mediaType = new MediaType()
                                .addExamples("default", loginExample);

                        operation.setRequestBody(new RequestBody()
                                .content(new Content()
                                        .addMediaType("application/json", mediaType)));
                    });
                }
            });
        };
    }
}