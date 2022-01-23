package com.example.weatherapp.common

data class Daily(

    val dt : Int,
    val temp : Temp,
    val weather : List<Weather>,
    val pop : Double
)
