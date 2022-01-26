package com.example.weatherapp.model.api

object OpenWeatherApiRetrofit {

    private const val BASE_URL = "https://api.openweathermap.org"
    val openWeatherApi: OpenWeatherApi
        get() = OpenWeatherApiRetrofitClient.getClient(BASE_URL).create(OpenWeatherApi::class.java)

}