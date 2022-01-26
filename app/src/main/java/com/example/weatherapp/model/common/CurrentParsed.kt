package com.example.weatherapp.model.common

data class CurrentParsed(val temp : String,
                         val feels_like : String,
                         val pressure : String,
                         val humidity : String,
                         val uvi : String,
                         val wind_speed : String,
                         val wind_deg : String,
                         val weatherDescription: String,
                         val weatherPicture : String)
