package com.example.weatherapp.model.common

data class Current(

    val dt : Int,
    val temp : Double,
    val feels_like : Double,
    val pressure : Int,
    val humidity : Int,
    val uvi : Int,
    val wind_speed : Double,
    val wind_deg : Int,
    val weather : List<Weather>
)
