package com.covid.top_countries.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.covid.top_countries.entities.Country;
import com.covid.top_countries.services.TopCovidCountryService;

@RestController
public class APIController {
	@Autowired
	private TopCovidCountryService CovidCountryService;
	
	@GetMapping("/health")
	public String health() {
		return "Service is reachable \n";
	}

	@GetMapping("/getTopCountries/{count}")
	public List<Country> topCountries(@PathVariable String count) {
		return this.CovidCountryService.getCountries(Long.parseLong(count));
	}
}
