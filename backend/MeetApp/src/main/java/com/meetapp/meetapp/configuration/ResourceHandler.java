package com.meetapp.meetapp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.nio.file.Paths;

@Configuration
public class ResourceHandler implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String currPath = Paths.get("src/main/pictures/").toAbsolutePath().toString();
        String location = "file:/" + currPath.replace("\\", "/") + "/";
        registry.addResourceHandler("/pictures/**")
                .addResourceLocations(location)
                .setCachePeriod(0)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}
