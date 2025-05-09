package com.nisum.directory.john;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class JohnApplication {

	public static void main(String[] args) {
		SpringApplication.run(JohnApplication.class, args);
	}

}
