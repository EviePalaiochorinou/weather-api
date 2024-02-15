package com.example.configuration;

import com.example.cache.Cache;
import com.example.client.OpenWeatherMapClient;
import com.example.client.OpenWeatherMapClientImpl;
import com.example.util.OpenWeatherMapConnectionHelper;
import com.example.util.URLBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeatherConfig {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Bean
    public OpenWeatherMapConnectionHelper openWeatherMapConnectionHelper() {
        OpenWeatherMapConnectionHelper helper = new OpenWeatherMapConnectionHelper();
        helper.setApiKey(apiKey);
        return helper;
    }

    @Bean
    public URLBuilder urlBuilder() {
        return new URLBuilder();
    }

    @Bean
    public Cache cache() { return new Cache(); }

    @Bean
    public OpenWeatherMapClient openWeatherMapClient() {
        return new OpenWeatherMapClientImpl(openWeatherMapConnectionHelper(), urlBuilder());
    }
}
