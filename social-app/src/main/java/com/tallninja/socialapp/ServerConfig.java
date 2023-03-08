package com.tallninja.socialapp;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
