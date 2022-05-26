package com.msntt.MSCredit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@EnableReactiveFeignClients
@EnableReactiveMongoRepositories
@SpringBootApplication
public class MsCreditApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCreditApplication.class, args);
	}

}
