package com.example.client;

public class OpenWeatherMapClientException extends Exception {
    public OpenWeatherMapClientException(String errorMessage, Throwable... ioException) {
        super(errorMessage);
    }
}
