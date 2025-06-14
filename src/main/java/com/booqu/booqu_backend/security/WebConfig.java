package com.booqu.booqu_backend.security;

import java.io.File;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.annotation.PostConstruct;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String basePath = System.getProperty("user.dir") + "/booqu-backend/uploads/";
        // Maps URL path /uploads/** to the physical folder on disk
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + basePath);
                // or absolute path example:
                // .addResourceLocations("file:/var/app/uploads/");
    }

    @PostConstruct
    public void logUploadPath() {
        String basePath = System.getProperty("user.dir") + "/booqu-backend/uploads/";
            System.out.println("==> STATIC FILES DIR MAPPED TO: " + basePath);
            File testFile = new File(basePath + "books/pdfs/1GG.pdf");
            System.out.println("==> Test file exists: " + testFile.exists());
    }
}