package com.example.weatherapp.model.common

data class BaseShortInfo(
    val lat : Double,
    val lon : Double,
    val timezone : String,
    val timezone_offset : Int,
    val current : Current,
)
