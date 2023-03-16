package com.covid.top_countries.services;

import java.util.ArrayList;
 import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
// import java.util.Collections;
// import java.util.Comparator;
// import java.util.HashMap;
import java.util.List;
 import java.util.Map;
 import java.util.Scanner;

import org.springframework.stereotype.Service;

 import java.io.FileInputStream;
 import java.io.IOException;
 import java.io.FileNotFoundException;

import com.covid.top_countries.entities.Country;

@Service
public class TopCovidCountryServiceImpl implements TopCovidCountryService {

	List<Country> list;

//	public TopCovidCountryServiceImpl() {
//		this.list = new ArrayList<>();
//		this.list.add(new Country("A", "B", 5));
//		this.list.add(new Country("X", "Y", 51));
//	}

	@Override
	public List<Country> getCountries(long count) {
		if (count > 0) {
			return this.list.subList(0, (int) count);			
		} else {
			return new ArrayList<>();
		}
	}


	public TopCovidCountryServiceImpl() throws IOException {
		// Path to CSV File in static folder
		String FILEPATH = System.getProperty("user.dir") + "/src/main/resources/static/CovidCasePerCountryPerMonth.csv";
		// Words in dataset which are not country
		// String[] StopWords = new String[] {"High income", "World"};
		// String[] StopWords = new String[] {"OWID_HIC", "OWID_WRL"};
		String[] StopWords = new String[] {"OWID"};
		
		// init file pointer and maps to count freq 
		FileInputStream inputStream = null;
		Scanner sc = null;
		Map <String, Long> CountryCasesCount = new HashMap<String, Long>();
		Map <String, String> CountryCode = new HashMap<String, String>();

		try {
			// iterate line by line without pulling full file in memory at once
			inputStream = new FileInputStream(FILEPATH);
			sc = new Scanner(inputStream, "UTF-8");

			// maintain a placeholder for country with max count of covid cases
			// since data is group by each country name in csv, we can continue to 
			// iterate over csv until we found record with another country name
			// in that case placholder variables hold the previous country name 
			// and max cases occured till date
			String prevCountryCode = ""; 
			long prevCasesCount = 0;
			
			// skip one line which is of csv headers
			sc.nextLine();
			// fetch one record from csv in memory then process then fetch another record
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] CurrentLine = line.split(",");
				String countryCode = CurrentLine[0]; 

				if (CurrentLine.length < 5 || countryCode.length() < 1 ||
				Arrays.stream(StopWords).anyMatch(countryCode::contains)) {
					// if current country is not a actual country then skip it
					continue;
				}
				// if the count of cases is empty string then skip record
				String countryCasesCount = CurrentLine[4];
				String countryName = CurrentLine[2];
				if (countryCasesCount.length() < 1 || countryName.length() < 1) {
					continue;
				}
				// pick only two columns - country name and it's covid count till date
				long casesTillNow = (long) Math.ceil(Double.valueOf(countryCasesCount));
				if (!CountryCode.containsKey(countryCode)) {
					CountryCode.put(countryCode, countryName);
				}
				// as soon the record moves to new country then save the prev country in mapping
				if (countryCode != prevCountryCode) {
					if (prevCountryCode != "") {
						CountryCasesCount.put(prevCountryCode, prevCasesCount);										
					}
				}
				// update the pointer with latest country and covid cases count
				// in csv - countries are given in sorted order of their names and count are aggregated
				// over each day. thus the last day for a country record states the max covid cases
				// occured in that country till date
				prevCountryCode = countryCode;
				prevCasesCount = casesTillNow;
			}
			// update the last country
			CountryCasesCount.put(prevCountryCode, prevCasesCount);	
			// note that Scanner suppresses exceptions
			if (sc.ioException() != null) {
				 throw sc.ioException();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				 inputStream.close();
			}
			if (sc != null) {
				sc.close();
			}
		}

		// look into map of country:covid_cases_count
		// for ( Map.Entry<String, Long> entry : CountryCasesCount.entrySet()) {
		// 	String key = entry.getKey();
		// 	long tab = entry.getValue();
		// 	System.out.printf("%s : %s \n", key, tab);
		// }
		System.out.println(CountryCasesCount.size());		
		List<String> TopCounties = new ArrayList<String>(CountryCasesCount.keySet());
		Collections.sort(TopCounties, new Comparator<String>() {
		    @Override
    		public int compare(String x, String y) {
        		return (int) (CountryCasesCount.get(y) - CountryCasesCount.get(x));
    		}
		});
		this.list = new ArrayList<>();
		for (String countryCode : TopCounties) {
			String countryName = CountryCode.get(countryCode);
			Country c = new Country(countryCode, countryName, CountryCasesCount.get(countryCode)); 
			this.list.add(c);
		}
		System.out.println(this.list.size());
	}
}
