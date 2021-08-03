package br.com.alura.challenge.backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Api AluraFlix",
                                description = "Plataforma compartilhamento de vídeo")
)
public class Swagger {
}