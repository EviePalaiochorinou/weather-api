package com.example;

import com.example.model.Forecast;
import com.example.model.WeatherResponseRecord;
import com.example.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    public WeatherService weatherService;

    @GetMapping("/summary")
    public List<WeatherResponseRecord> weatherSummary(
            @RequestParam("unit") String unit,
            @RequestParam("temperature") int temperature,
            @RequestParam("cities") String cities) {

        return weatherService.getWeatherSummary(unit, temperature, cities);
    }

    @GetMapping("/cities/{cityId}")
    public List<Forecast> getCityWeather(@PathVariable String cityId) throws ResponseStatusException {
        try {
            return weatherService.getCityWeather(cityId);
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
