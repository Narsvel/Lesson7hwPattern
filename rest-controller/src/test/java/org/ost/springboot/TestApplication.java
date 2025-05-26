package org.ost.springboot;

import org.springframework.boot.SpringApplication;

public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.from(RestApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
