package br.com.carloser7.asstecnica.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CorsConfig.class);

    @Value("${api.security.cors.origin}")
    private String origins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        LOGGER.info("Configuring CORS with allowed origins: {}", this.origins);
        registry.addMapping("/**")
            .allowedOrigins(this.origins)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
    }
    
}
