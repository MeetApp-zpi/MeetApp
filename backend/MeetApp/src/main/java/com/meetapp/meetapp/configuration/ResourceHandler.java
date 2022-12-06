package com.meetapp.meetapp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.nio.file.Paths;
import java.util.Locale;

@Configuration
public class ResourceHandler implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String currPath = Paths.get("src/main/pictures/").toAbsolutePath().toString();
        String OS = System.getProperty("os.name", "unknown").toLowerCase(Locale.ROOT);
        String location;
        if (OS.contains("win")) {
            location = "file:/" + currPath.replace("\\", "/") + "/";
        } else {
            location = "file:" + currPath.replace("\\", "/") + "/";
        }
        registry.addResourceHandler("/pictures/**")
                .addResourceLocations(location)
                .setCachePeriod(0)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}
