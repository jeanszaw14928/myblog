package com.personal.myblog.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${upload.path}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // လမ်းကြောင်းရှေ့မှာ file: ပါရပါမယ် (ဥပမာ - file:/home/user/uploads/)
        String location = "file:" + uploadDir;
        if (!location.endsWith("/")) {
            location += "/";
        }

        registry.addResourceHandler("/images/**")
                .addResourceLocations(location);
    }
}

