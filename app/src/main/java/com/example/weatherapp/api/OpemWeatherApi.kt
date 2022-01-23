package com.example.weatherapp.api

import com.example.weatherapp.model.common.BaseFullInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("data/2.5/onecall")
        fun getFullInfo(@Query("lat") latitude: Double): Call<BaseFullInfo>
}
