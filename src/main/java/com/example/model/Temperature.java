package com.example.model;

public record Temperature(float temp, float temp_min, float temp_max) {

    public Temperature toFahrenheit() {
        return new Temperature(((this.temp * 9/5) + 32), ((this.temp_min * 9/5) + 32), ((this.temp_max * 9/5) + 32));
    }
}
