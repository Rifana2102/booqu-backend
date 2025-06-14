package com.booqu.booqu_backend.security;

import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String basePath = Paths.get("").toAbsolutePath().toString() + "/uploads/";
        // Maps URL path /uploads/** to the physical folder on disk
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + basePath);
                // or absolute path example:
                // .addResourceLocations("file:/var/app/uploads/");
    }
    
}