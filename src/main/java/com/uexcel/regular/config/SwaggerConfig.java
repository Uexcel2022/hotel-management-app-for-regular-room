package com.uexcel.regular.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@AllArgsConstructor
public class SwaggerConfig {
//    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Hotel Mgmt Regular Room")
                        .description("This is a sample hotel mgmt regular room.")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringBoot uexcel Documentation")
                        .url("https://springboot.uexcel.github.org/docs"))
                .externalDocs(new ExternalDocumentation()
                .description("Logout")
                        .url("LOGOUT_PATH"));

    }

//    @Bean
//    public OpenAPI openAPI() {
//        return new OpenAPI().addSecurityItem(new SecurityRequirement().
//                        addList("Client Credential"))
//                .components(new Components().addSecuritySchemes
//                        ("Bearer Authentication", createAPIKeyScheme()))
//                .info(new Info().title("My REST API")
//                        .description("JWT API Demo.")
//                        .version("1.0").contact(new Contact().name("Uexcel")
//                                .email( "www.github.com").url("uexcel@gmail.com"))
//                        .license(new License().name("License of API")
//                                .url("API license URL")));
//    }

    @Configuration
    public class OpenAPISecurityConfig {

        @Value("${keycloak.auth-server-url}")
        String authServerUrl;
        @Value("${keycloak.token-url}")
        String tokenUrl;

        private static final String OAUTH_SCHEME_NAME = "my_oAuth_security_schema";

        @Bean
        public OpenAPI openAPI() {
            return new OpenAPI().components(new Components()
                            .addSecuritySchemes(OAUTH_SCHEME_NAME, createOAuthScheme()))
                    .addSecurityItem(new SecurityRequirement().addList(OAUTH_SCHEME_NAME))
                    .info(new Info().title("Todos Management Service")
                            .description("A service providing todos.")
                            .version("1.0"));
        }

        private SecurityScheme createOAuthScheme() {
            OAuthFlows flows = createOAuthFlows();
            return new SecurityScheme().type(SecurityScheme.Type.OAUTH2)
                    .flows(flows);
        }

        private OAuthFlows createOAuthFlows() {
            OAuthFlow flow = createAuthorizationCodeFlow();
            return new OAuthFlows().authorizationCode(flow);
        }

        private OAuthFlow createAuthorizationCodeFlow() {
            return new OAuthFlow()
                    .authorizationUrl(authServerUrl)
                    .tokenUrl(tokenUrl);

//                    .scopes(new Scopes().addString("read_access", "read data")
//                            .addString("write_access", "modify data"));
        }
    }

}