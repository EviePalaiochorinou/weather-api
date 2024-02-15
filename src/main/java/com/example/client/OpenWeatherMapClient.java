package com.example.client;

import com.example.model.WeatherResponseRecord;

public interface OpenWeatherMapClient {

    WeatherResponseRecord getWeatherForecast(String cityId) throws OpenWeatherMapClientException;
}
