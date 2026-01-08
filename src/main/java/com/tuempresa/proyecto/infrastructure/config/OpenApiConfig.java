package com.tuempresa.proyecto.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API de Productos - Clean Architecture")
                .version("1.0.0")
                .description("API REST para gestión de productos implementada con Clean Architecture en Spring Boot")
                .contact(new Contact()
                    .name("Tu Empresa")
                    .email("contacto@tuempresa.com")
                    .url("https://www.tuempresa.com"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
            .servers(List.of(
                new Server()
                    .url("http://localhost:8080")
                    .description("Servidor de Desarrollo"),
                new Server()
                    .url("https://api.tuempresa.com")
                    .description("Servidor de Producción")
            ));
    }
}
