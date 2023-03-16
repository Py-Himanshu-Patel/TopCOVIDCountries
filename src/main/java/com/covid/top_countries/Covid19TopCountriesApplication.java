package com.covid.top_countries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class Covid19TopCountriesApplication {

	public static void main(String[] args) {
		SpringApplication.run(Covid19TopCountriesApplication.class, args);
	}

}
