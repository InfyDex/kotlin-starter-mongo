package com.unburden.swagger

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun virtualTradingSwagger(): OpenAPI {
        return OpenAPI()
            .info(
                Info().title("Unburden Service API Specifications")
                    .version("v0.0.1")
            )
    }
}