package com.example.cache;

import com.example.model.WeatherResponseRecord;

import java.util.HashMap;

public class Cache {
    private final HashMap<String, WeatherResponseRecord> cache = new HashMap<>();

    public WeatherResponseRecord getForecasts(String cityId) {
        return cache.get(cityId);
    }
    public void setForecasts(String cityID, WeatherResponseRecord weatherResponse) {
        System.out.printf("Setting, %s, %s\n", cityID, weatherResponse.city().name());
        cache.put(cityID, weatherResponse);
    }
}