package com.polytech.commandes.com.polytech.commandes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Gestion de Commandes")
                        .version("1.0.0")
                        .description("API REST pour la gestion des commandes, clients et produits")
                        .contact(new Contact()
                                .name("Institut Polytechnique Saint-Louis")
                                .email("ssidibe@ept.edu.sn")
                                .url("https://www.polytechnique.sn")))
                .addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("Serveur de développement"))
                .addServersItem(new Server()
                        .url("https://api.production.com")
                        .description("Serveur de production"));
    }
}
