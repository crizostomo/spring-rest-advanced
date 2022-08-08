package com.developer.beverageapi;

import com.developer.beverageapi.infrastructure.repository.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class) //We substitute 'SimpleJpaRepository' for 'CustomJpaRepository'
public class BeverageApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeverageApiApplication.class, args);
	}

}
