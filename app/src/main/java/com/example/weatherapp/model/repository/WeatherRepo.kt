package com.example.weatherapp.model.repository

import com.example.weatherapp.model.common.BaseFullInfo
import io.reactivex.Observable

interface WeatherRepo {

    fun getFullInfo(latitude: String,
                    longitude: String,
                    lang: String) : Observable<BaseFullInfo>
}