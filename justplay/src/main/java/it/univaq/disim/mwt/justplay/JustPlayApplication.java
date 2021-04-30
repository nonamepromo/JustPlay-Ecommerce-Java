package it.univaq.disim.mwt.justplay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import it.univaq.disim.mwt.justplay.presentation.VideogiocoController;

@SpringBootApplication
public class JustPlayApplication {

	public static void main(String[] args) {
		SpringApplication.run(JustPlayApplication.class, args);
	}

}
