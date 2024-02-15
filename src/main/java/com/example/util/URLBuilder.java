package com.example.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class URLBuilder {

    private String apiKey;
    private String propertiesPath = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"overrides.properties";
    private Properties appProps = new Properties();
    private final String baseURL = "https://api.openweathermap.org/data/2.5/forecast?";

    public URLBuilder() {
    }

    public URL buildApiCityWeatherURL(String cityId) throws IOException {
        appProps.load(new FileInputStream(propertiesPath));
        String urlString =
                baseURL
                        + "id="
                        + cityId
                        + "&units=metric"
                        + "&appid="
                        + appProps.getProperty("openweathermap.api.key");
        return new URL(urlString);
    }
}