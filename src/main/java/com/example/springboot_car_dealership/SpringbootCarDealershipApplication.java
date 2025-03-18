package com.example.springboot_car_dealership;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.example.springboot_car_dealership.infrastructure.repository")
public class SpringbootCarDealershipApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCarDealershipApplication.class, args);
	}

}
