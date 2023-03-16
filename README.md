# TopCOVIDCountries

### Run the Project
```
cd "/home/himanshu/Downloads/top_countries/src/main/java/com/covid/top_countries/" && javac Covid19TopCountriesApplication.java && java Covid19TopCountriesApplication
```

Hit API on port localhost:8080 along with a positive integer (0 to 200) as path parameter.
```
http://localhost:8080/getTopCountries/{top-n-countries}  
```

Main File for project start.
```
src/main/java/com/covid/top_countries/Covid19TopCountriesApplication.java
```

#### Some Key Highlights
- Not reading entire CSV file but reading line by line processing.
- This prevent us from dumping all of the data in RAM.
- Picking up the record for each country from CSV while proper error handeling. Using only valid data and skipping invalid data (non countries, empty string etc).
- Making Country Entities and populating the ArrayList with these Country Entities.
- Sorting ArrayList based on number of cases (inside constructor of service) and caching this list to respond for query faster.
- Making a health check endpoint on controller to check for server availability, in case other endpoints are down.

### Sample

**Format**: http://localhost:8080/getTopCountries/{**top-n-countries**}  

Put a positive integer inplace of : **top-n-countries**

---

Request with negative of 0 as input
```bash
$ curl http://localhost:8080/getTopCountries/-1 | json_pp
[]
```

Request with positive input
```bash
$ curl http://localhost:8080/getTopCountries/1 | json_pp
[
   {
      "caseCount" : 102247392,
      "countryCode" : "USA",
      "countryName" : "United States"
   }
]
```

```bash
$ curl http://localhost:8080/getTopCountries/5 | json_pp
[
   {
      "caseCount" : 102247392,
      "countryCode" : "USA",
      "countryName" : "United States"
   },
   {
      "caseCount" : 99109603,
      "countryCode" : "CHN",
      "countryName" : "China"
   },
   {
      "caseCount" : 44688367,
      "countryCode" : "IND",
      "countryName" : "India"
   },
   {
      "caseCount" : 38538948,
      "countryCode" : "FRA",
      "countryName" : "France"
   },
   {
      "caseCount" : 38221663,
      "countryCode" : "DEU",
      "countryName" : "Germany"
   }
]
```
---