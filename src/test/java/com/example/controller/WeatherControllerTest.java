package com.example.controller;

import com.example.WeatherController;
import com.example.client.OpenWeatherMapClientException;
import com.example.model.CityInfo;
import com.example.model.Forecast;
import com.example.model.Temperature;
import com.example.model.WeatherResponseRecord;
import com.example.service.WeatherService;
import com.example.util.DateTimeGenerator;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WeatherControllerTest {

    List<WeatherResponseRecord> expectedResponse = new ArrayList<WeatherResponseRecord>(List.of(
       new WeatherResponseRecord(List.of(
               new Forecast(new Temperature(20F, 10F, 30F), DateTimeGenerator.tomorrowNoon().format(DateTimeGenerator.myFormat()))
            ), new CityInfo(2618425, "Copenhagen")),
       new WeatherResponseRecord(List.of(
               new Forecast(new Temperature(20F, 10F, 30F), DateTimeGenerator.tomorrowNoon().format(DateTimeGenerator.myFormat()))
            ), new CityInfo(3621849, "San José"))
    ));

    public WeatherController weatherController = new WeatherController();
    public WeatherService weatherService = mock(WeatherService.class);

    @Test
    public void test_returns_list_of_weather_response_records() throws OpenWeatherMapClientException {
        this.weatherController.weatherService = this.weatherService;
        String unit = "celsius";
        int temperature = 20;
        String cities = "Copenhagen, San José";

        when(this.weatherController.weatherService.getWeatherSummary(unit, temperature, cities)).thenReturn(expectedResponse);
        List<WeatherResponseRecord> actualWeatherSummary = weatherController.weatherSummary(unit, temperature, cities);
        assertEquals(expectedResponse, actualWeatherSummary);
    }

    @Test
    public void test_returns_correct_number_of_weather_response_records() throws OpenWeatherMapClientException {
        this.weatherController.weatherService = this.weatherService;

        String unit = "celsius";
        int temperature = 20;
        String cities = "Copenhagen, San José";

        when(weatherController.weatherService.getWeatherSummary(unit, temperature, cities)).thenReturn(expectedResponse);

        List<WeatherResponseRecord> actualWeatherSummary = weatherController.weatherSummary(unit, temperature, cities);
        assertEquals(expectedResponse.size(), actualWeatherSummary.size());
    }

    @Test
    public void test_returns_empty_list_if_cities_parameter_is_empty() throws OpenWeatherMapClientException {
        this.weatherController.weatherService = this.weatherService;

        String unit = "celsius";
        int temperature = 20;
        String cities = "";

        List<WeatherResponseRecord> actualWeatherSummary = weatherController.weatherSummary(unit, temperature, cities);
        assertTrue(actualWeatherSummary.isEmpty());
    }

    @Test
    public void test_returns_empty_list_if_no_cities_match_the_criteria() throws OpenWeatherMapClientException {
        this.weatherController.weatherService = this.weatherService;

        String unit = "celsius";
        int temperature = 20;
        String cities = "London,Paris,Berlin";

        List<WeatherResponseRecord> actualWeatherSummary = weatherController.weatherSummary(unit, temperature, cities);
        assertTrue(actualWeatherSummary.isEmpty());
    }
}