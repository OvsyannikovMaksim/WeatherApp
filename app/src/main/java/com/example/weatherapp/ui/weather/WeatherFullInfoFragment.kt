package com.example.weatherapp.ui.weather

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.CurrentMapper
import com.example.weatherapp.model.api.OpenWeatherApiRetrofit
import com.example.weatherapp.model.common.BaseFullInfo
import com.example.weatherapp.databinding.FragmentWeatherFullInfoBinding
import com.example.weatherapp.model.repository.WeatherRepoImpl
import com.squareup.picasso.Picasso
import java.util.*

class WeatherFullInfoFragment : Fragment() {

    private lateinit var weatherFullInfoViewModel: WeatherFullInfoViewModel
    private lateinit var weatherFullInfoViewModelFactory: WeatherFullInfoViewModelFactory
    private var binding: FragmentWeatherFullInfoBinding? = null

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?
    ): View{
        binding = FragmentWeatherFullInfoBinding.inflate(inflater)
        val weatherRepo = WeatherRepoImpl(OpenWeatherApiRetrofit.openWeatherApi)
        weatherFullInfoViewModelFactory = WeatherFullInfoViewModelFactory(weatherRepo)
        weatherFullInfoViewModel= ViewModelProvider(this, weatherFullInfoViewModelFactory)[WeatherFullInfoViewModel::class.java]
        weatherFullInfoViewModel.getWeather("55.80945", "37.95806", Locale.getDefault().country)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val currentMapper=CurrentMapper()
        super.onViewCreated(view, savedInstanceState)
        val weather: LiveData<BaseFullInfo> = weatherFullInfoViewModel.weather
        weather.observe(viewLifecycleOwner) {
            val currentWeather = currentMapper.map(it.current, view.context)
            binding?.placeName?.text = it.timezone
            binding?.humidity?.text=currentWeather.humidity
            binding?.temperature?.text=currentWeather.temp
            binding?.UV?.text=currentWeather.uvi
            binding?.feelTemperature?.text=currentWeather.feels_like
            binding?.pressure?.text=currentWeather.pressure
            binding?.weatherName?.text = currentWeather.weatherDescription
            Picasso.with(view.context).load(currentWeather.weatherPicture).into(binding?.weatherPic)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        weatherFullInfoViewModel.clear()
    }
}