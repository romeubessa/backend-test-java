package com.fcamara.parking.management.controllers.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Parking Management API",
                version = "v1.0",
                description = "API for managing parking establishment and vehicle transactions.",
                contact = @Contact(name = "Romeu Bessa", email = "romeubessa10@gmail.com"),
                license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html")
        )
)
public class OpenApiConfig {
}
