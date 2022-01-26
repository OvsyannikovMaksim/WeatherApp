package com.example.weatherapp.model.api

import com.example.weatherapp.model.common.BaseFullInfo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("data/2.5/onecall")
        fun getFullInfo(@Query("lat") latitude: String,
                        @Query("lon") longitude: String,
                        @Query("exclude") exclude: String,
                        @Query("lang") lang: String,
                        @Query("units") units: String,
                        @Query("appid") appid: String): Observable<BaseFullInfo>
}
