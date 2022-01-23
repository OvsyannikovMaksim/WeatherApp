package com.example.weatherapp.model.common

data class Daily(

    val dt : Int,
    val temp : Temp,
    val weather : List<Weather>,
    val pop : Double
)
