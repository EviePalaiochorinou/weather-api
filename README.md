## 📝 REST API for Weather Data

A REST API that exposes weather data as JSON to be used by a mobile app.  
It takes weather data from the (external) Open Weather Map API, stores the data in memory, and limits the requests to the external API to 10,000 requests per day.

The API only makes requests to Open Weather Map if the data requested are not in cache, therefore ensuring the rate limit towards the 3rd party API is not exceeded while still serving data to the apps.

### This project is made with:
- Java17
- Spring Boot
- Bucket4j (for rate limiting)
- Junit (for testing)
- Mockito (for test mocks)
- Maven

## Running The Project Locally

### Installation
This requires the repository to be cloned locally and the current directory to be the root of the repo.

1. Clone the repository from
    ```sh
   https://github.com/EviePalaiochorinou/weather-api.git
    ```
2. Put your own API key in the `overrides.properties` file.

3. Build this Spring Boot Project with Maven mvn package or `mvn install` / `mvn clean install`
4. Run this Spring Boot app using Maven: `mvn spring-boot:run`


Now you can test the endpoints manually
  ```sh
  http://localhost:8080/weather/summary?unit=celsius&temperature=10&cities=2618425,3621849,3133880
  
  AND
  
  http://localhost:8080//weather/cities/2618425
  ``` 
Or use the http client of your choice

### Testing

You can run the tests using:
```shell script
mvn test
```
Or by right clicking on run on the test package in Intellij.

## 🖊️ Implementation

The API exposes the following two endpoints:

### `GET /weather/summary`

Returns a list of the user’s favourite cities' forecast if the temperature will be above a certain temperature the next day. The client should pass the parameters as part of the request.

**Parameters**
* unit (celsius|fahrenheit)
* temperature (int)
* cities (city IDs separated by comma)

Example request: `/weather/summary?unit=celsius&temperature=24&cities=2618425,3621849,3133880`

### `GET /weather/cities/<city_id>`

A list of temperatures for the next 5 days in one specific city. Default temperature unit is Celsius.

**Parameters**

* city_id

Example request: `/weather/cities/2618425`

## Requirements

### 📋 Specifications
1. The API uses OpenWeatherMap as the 3rd party weather API.
2. A rate limiter is implemented with the bucket4j library that allows only 10,000 daily requests to the external API.
3. The default temperature unit is Celsius so that we only have to change the response when the user requests the temperature in Fahrenheit.
4. There is a cache that stores the data from OpenWeatherMap in memory in a HashMap with the city id as a key and a WeatherResponseRecord object as the value.
5. The WeatherService layer checks that the limit has not been surpassed, then it only requests the data from the external API if it does not find the requested data in the cache memory. This way we ensure that we limit our requests to OpenWeatherMap.
6. When a new request is made to OpenWeatherMap, then that data is saved in cache.

## Implementation

### 💻 Error Handling

- In WeatherService's getWeatherSummary method, any exception caught is handled by the method, so the controller does not need to do error handling for now.
- A **custom** exception was created for when the request to the OpenWeatherMap API fails.
- In the future, a custom rate limiter exception would be created.
- Future work: If the city requested was not in cache and the api limit was over, then we could return the closest city available.


### 💻 Testing

- The WeatherService is mocked in order to test the WeatherController.
- Future work includes to aim for 100% test coverage and include tests for all edge cases, plus testing that exceptions are raised for the /populate endpoint.

### 💻 Assumptions

- Since we do not have logs, in some cases the error caught is printed instead.

### 💬 Future Work

- Ensure that the data served from cache are still fresh (agree on what counts as 'fresh'). If not fresh, request new data if the limit allows.
- Simplify our APIs response so that it is even easier to consume.
- Test all the logic of the WeatherService. Include tests for all edge cases for both the WeatherController and the WeatherService.
- Add a database to store the weather data for resilience, scalability, and advanced handling, instead of keeping the data in memory.
- The min temperature parameter requested by the user in the /weather/summary/ endpoint should be transformed in the type of unit also requested by the user.