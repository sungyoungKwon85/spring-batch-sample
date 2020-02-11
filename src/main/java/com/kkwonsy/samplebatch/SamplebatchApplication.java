package com.kkwonsy.samplebatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableBatchProcessing
@EnableScheduling
@EnableJpaRepositories(basePackages = "com.kkwonsy.samplebatch")
@SpringBootApplication
public class SamplebatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SamplebatchApplication.class, args);
	}

}
