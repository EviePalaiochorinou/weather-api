package com.example.client;

import com.example.model.WeatherResponseRecord;
import com.example.util.OpenWeatherMapConnectionHelper;
import com.example.util.URLBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenWeatherMapClientImpl implements OpenWeatherMapClient {

    private final OpenWeatherMapConnectionHelper openWeatherMapConnectionHelper;
    private final URLBuilder urlBuilder;
    private final Gson gson;

    public OpenWeatherMapClientImpl(OpenWeatherMapConnectionHelper openWeatherMapConnectionHelper, URLBuilder urlBuilder) {
        this.openWeatherMapConnectionHelper = openWeatherMapConnectionHelper;
        this.urlBuilder = urlBuilder;
        this.gson = new Gson();
    }

    public WeatherResponseRecord getWeatherForecast(String cityId) throws OpenWeatherMapClientException {
        try {
            URL url = urlBuilder.buildApiCityWeatherURL(cityId);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            StringBuilder response = openWeatherMapConnectionHelper.tryUrlHttpRequest(request);
            System.out.println("Requesting from Open weather api");

            Type results = new TypeToken<WeatherResponseRecord>() {}.getType();

            return gson.fromJson(String.valueOf(response), results);
        } catch (IOException e) {
            throw new OpenWeatherMapClientException("Request failed. Possible causes: remote server unavailable, network connection failure, could not parse response.", e.getCause());
        }
    }

}
