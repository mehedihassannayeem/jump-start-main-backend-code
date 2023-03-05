package com.jumpstart;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.jumpstart.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class JumpStartApplication {

	public static void main(String[] args) {
		SpringApplication.run(JumpStartApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
