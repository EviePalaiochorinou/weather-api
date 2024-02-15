package com.example.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.nio.charset.StandardCharsets;

public class OpenWeatherMapConnectionHelper {

    private String apiKey;

    public OpenWeatherMapConnectionHelper() {
    }

    public StringBuilder tryUrlHttpRequest(HttpURLConnection request) {
        StringBuilder response;
        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8))) {
            response = buffer(reader);
            return response;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            response = new StringBuilder("Error");
            return response;
        }
    }

    public void setOpenWeatherMapPropertiesAndHeaders(HttpURLConnection request) throws ProtocolException {
        request.setRequestMethod("GET");
        request.setRequestProperty("X-Application", apiKey);
        request.setRequestProperty("Accept", "application/json");
        request.setDoOutput(true);
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    private StringBuilder buffer(BufferedReader reader) throws IOException {
        StringBuilder response = new StringBuilder();
        String responseLine;
        while ((responseLine = reader.readLine()) != null) {
            response.append(responseLine.trim());
        }
        return response;
    }

}
