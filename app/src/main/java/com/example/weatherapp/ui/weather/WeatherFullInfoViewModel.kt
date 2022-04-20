package com.example.weatherapp.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.common.BaseFullInfo
import com.example.weatherapp.model.repository.WeatherRepo
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class WeatherFullInfoViewModel(private val weatherRepo: WeatherRepo) : ViewModel() {
    private lateinit var disposable: Disposable
    val weather: MutableLiveData<BaseFullInfo> = MutableLiveData()


    fun getWeather(latitude: Double,
                   longitude: Double,
                   lang: String){


        disposable = weatherRepo.getFullInfo(latitude.toString(), longitude.toString(), lang)
            .subscribeOn(Schedulers.io())
            .subscribe { weather.postValue(it) }
    }

    fun clear(){
        disposable.dispose()
    }
}