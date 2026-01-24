package com.Spring.elitedemo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class Elitedemo1Application {

	public static void main(String[] args) {
		SpringApplication.run(Elitedemo1Application.class, args);
	}

}
