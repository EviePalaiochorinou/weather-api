package com.example.model;

public record Forecast(Temperature main, String dt_txt) {
    public Forecast toFahrenheit() {
        return new Forecast(this.main().toFahrenheit(), this.dt_txt);
    }
}
