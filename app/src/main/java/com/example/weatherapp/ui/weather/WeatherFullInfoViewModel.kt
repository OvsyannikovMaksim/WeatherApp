package com.example.weatherapp.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.common.BaseFullInfo
import com.example.weatherapp.model.repository.WeatherRepo
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class WeatherFullInfoViewModel(private val weatherRepo: WeatherRepo) : ViewModel() {
    private lateinit var disposable: Disposable
    val weather: MutableLiveData<BaseFullInfo> = MutableLiveData()

    fun getWeather(latitude: String,
                   longitude: String,
                   lang: String){
        disposable = weatherRepo.getFullInfo(latitude, longitude, lang)
            .subscribeOn(Schedulers.io())
            .subscribe { weather.postValue(it) }
    }

    fun clear(){
        disposable.dispose()
    }
}