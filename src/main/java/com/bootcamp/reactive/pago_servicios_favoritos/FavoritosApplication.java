package com.bootcamp.reactive.pago_servicios_favoritos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class FavoritosApplication {

	public static void main(String[] args) {
		SpringApplication.run(FavoritosApplication.class, args);
	}

}
