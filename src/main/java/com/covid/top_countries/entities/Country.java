package com.covid.top_countries.entities;

public class Country{
	
	private String countryCode;
	private String countryName;
	private long caseCount;

	public Country(String id, String title, long caseCount) {
		super();
		this.setCountryCode(id);
		this.setCountryName(title);
		this.setCaseCount(caseCount);
	}

	@Override
	public String toString() {
		return "TopCountries [countryCode=" + countryCode + ", countryName=" + countryName + ", caseCount=" + caseCount
				+ "]";
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public long getCaseCount() {
		return caseCount;
	}

	public void setCaseCount(long caseCount) {
		this.caseCount = caseCount;
	}
}
