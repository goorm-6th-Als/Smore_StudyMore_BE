package com.als.SMore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilderFactory;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcLinkBuilderFactory linkBuilderFactory() {
        return new WebMvcLinkBuilderFactory();
    }
}
