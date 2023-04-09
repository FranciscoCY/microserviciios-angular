package com.frank.inmueble.microinmueble;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroInmuebleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroInmuebleApplication.class, args);
	}

}
