package com.englishforit.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@org.springframework.boot.context.properties.EnableConfigurationProperties(com.englishforit.backend.config.RsaKeyProperties.class)
public class EnglishForItApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnglishForItApplication.class, args);
	}

}
