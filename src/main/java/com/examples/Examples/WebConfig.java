package com.examples.Examples;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // This maps the URL "/uploads/**" to your physical folder
        registry.addResourceHandler("/photos/**")
                .addResourceLocations("file:/Users/venkateshwarausirikayala/IdeaProjects/Examples/uploads/students/");
    }
}