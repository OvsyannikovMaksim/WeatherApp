package com.example.weatherapp.model.repository

import com.example.weatherapp.model.api.OpenWeatherApi
import com.example.weatherapp.model.common.BaseFullInfo
import io.reactivex.Observable


class WeatherRepoImpl(val openWeatherApi: OpenWeatherApi):WeatherRepo {
    private val API_KEY_OPEN_WEATHER: String = "4eddd7394f54a0dd81465aa802a837f5"
    private val EXCLUDE_FULL: String = "minutely,hourly,alerts"

    override fun getFullInfo(latitude: String,
                             longitude: String,
                             lang: String): Observable<BaseFullInfo> {
        return openWeatherApi.getFullInfo(latitude, longitude, EXCLUDE_FULL, lang,"metric" , API_KEY_OPEN_WEATHER)
    }
}