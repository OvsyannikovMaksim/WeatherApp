package com.example.weatherapp.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.model.repository.WeatherRepo

class WeatherFullInfoViewModelFactory(private val weatherRepo: WeatherRepo)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherFullInfoViewModel::class.java)) {
            return WeatherFullInfoViewModel(weatherRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}