package com.covid.top_countries.services;

import java.util.List;
import com.covid.top_countries.entities.Country;

public interface TopCovidCountryService {
	public List<Country> getCountries(long count);
}
