package com.example.service;

import com.example.cache.Cache;
import com.example.client.OpenWeatherMapClient;
import com.example.client.OpenWeatherMapClientException;
import com.example.model.Forecast;
import com.example.model.Temperature;
import com.example.model.WeatherResponseRecord;
import com.example.util.DateTimeGenerator;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {

    private final Cache cache;
    private final OpenWeatherMapClient openWeatherMapClient;
    private final Bucket requestBucket;

    public WeatherService(Cache cache, OpenWeatherMapClient openWeatherMapClient, Bucket requestBucket) {
        this.cache = cache;
        this.openWeatherMapClient = openWeatherMapClient;
        this.requestBucket = requestBucket;
    }

    public List<Forecast> getCityWeather(String cityId) throws Exception {

        ConsumptionProbe probe = requestBucket.tryConsumeAndReturnRemaining(1);
        WeatherResponseRecord cachedCityWeatherForecast = cache.getForecasts(cityId);
        if (probe.isConsumed()) {
            if (cachedCityWeatherForecast != null) {
                return cachedCityWeatherForecast.list();
            }
            try {
                WeatherResponseRecord cityWeatherForecast = openWeatherMapClient.getWeatherForecast(cityId);
                cache.setForecasts(cityId, cityWeatherForecast);
                return cityWeatherForecast.list();
            } catch (OpenWeatherMapClientException e) {
                throw new Exception(e.getMessage());
            }
        } else {
            return cachedCityWeatherForecast.list();
        }
    }

    public List<WeatherResponseRecord> getWeatherSummary(String unit, int temperature, String cities) {
        ConsumptionProbe probe = requestBucket.tryConsumeAndReturnRemaining(1);
        String[] citiesList = cities.split(",");
        List<WeatherResponseRecord> weatherSummary = new ArrayList<>();

        for (String city : citiesList) {
            WeatherResponseRecord weatherForecast = cache.getForecasts(city);
            if ((weatherForecast == null) && (probe.isConsumed())){
                try {
                    weatherForecast = openWeatherMapClient.getWeatherForecast(city);
                } catch (OpenWeatherMapClientException e) {
                    continue;
                }
                cache.setForecasts(city, weatherForecast);
            }

            for (Forecast forecast : weatherForecast.list()) {
                if (DateTimeGenerator.tomorrowNoon().format(DateTimeGenerator.myFormat()).equals(forecast.dt_txt()) && forecast.main().temp() >= (float) temperature){
                    if (unit.equals("fahrenheit")) {
                        Temperature tempInFahrenheit = forecast.main().toFahrenheit();
                        // Create a new forecast with the temperature in Fahrenheit
                        Forecast forecastInFahrenheit = new Forecast(tempInFahrenheit, forecast.dt_txt());
                        weatherForecast.list().add(forecastInFahrenheit);
                        weatherSummary.add(weatherForecast.forecastToFahrenheit());
                    } else {
                        weatherSummary.add(weatherForecast);
                    }
                    break;
                }
            }
        }
        return weatherSummary;
    }

}
