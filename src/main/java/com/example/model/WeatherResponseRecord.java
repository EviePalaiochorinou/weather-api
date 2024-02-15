package com.example.model;

import java.util.ArrayList;
import java.util.List;

public record WeatherResponseRecord(List<Forecast> list, CityInfo city) {
    public WeatherResponseRecord forecastToFahrenheit() {
        List<Forecast> forecastToFahrenheit = new ArrayList<>();
        for (Forecast forecast : this.list()) {
            forecastToFahrenheit.add(forecast.toFahrenheit());
        }
        return new WeatherResponseRecord(forecastToFahrenheit, this.city);
    }
}
