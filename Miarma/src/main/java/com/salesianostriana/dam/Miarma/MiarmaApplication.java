package com.salesianostriana.dam.Miarma;

import com.salesianostriana.dam.Miarma.config.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class MiarmaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiarmaApplication.class, args);
	}

}
