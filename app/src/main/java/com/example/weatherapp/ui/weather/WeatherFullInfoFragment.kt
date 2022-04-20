package com.example.weatherapp.ui.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.CurrentMapper
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherFullInfoBinding
import com.example.weatherapp.model.api.OpenWeatherApiRetrofit
import com.example.weatherapp.model.common.BaseFullInfo
import com.example.weatherapp.model.repository.WeatherRepoImpl
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest.PRIORITY_LOW_POWER
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Task
import com.squareup.picasso.Picasso
import java.util.*


class WeatherFullInfoFragment : Fragment() {

    private lateinit var weatherFullInfoViewModel: WeatherFullInfoViewModel
    private lateinit var weatherFullInfoViewModelFactory: WeatherFullInfoViewModelFactory
    private var binding: FragmentWeatherFullInfoBinding? = null
    private lateinit var requestPermissionLauncher : ActivityResultLauncher<String>
    private lateinit var currentLocation: Location
    private val geocoder: Geocoder by lazy {
        Geocoder(requireContext().applicationContext, Locale.getDefault())
    }
    private val fusedLocationClient: FusedLocationProviderClient by lazy{
        LocationServices.getFusedLocationProviderClient(requireContext().applicationContext)
    }
    private var cancellationTokenSource = CancellationTokenSource()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    Log.d("requestPermissionLauncher", "if $isGranted")
                    requestLocation()
                } else {
                    Log.d("requestPermissionLauncher", "else $isGranted")
                    Toast.makeText(requireContext().applicationContext, R.string.PermissionToastText,Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?
    ): View{

        binding = FragmentWeatherFullInfoBinding.inflate(inflater)
        val weatherRepo = WeatherRepoImpl(OpenWeatherApiRetrofit.openWeatherApi)
        weatherFullInfoViewModelFactory = WeatherFullInfoViewModelFactory(weatherRepo)
        weatherFullInfoViewModel= ViewModelProvider(this, weatherFullInfoViewModelFactory)[WeatherFullInfoViewModel::class.java]
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        initRefresh()
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val currentMapper=CurrentMapper()
        super.onViewCreated(view, savedInstanceState)
        val weather: LiveData<BaseFullInfo> = weatherFullInfoViewModel.weather
        weather.observe(viewLifecycleOwner) {
            val currentWeather = currentMapper.map(it.current, view.context)
            if(!binding?.placePic?.isVisible!!) {
                binding?.placePic?.visibility = View.VISIBLE
            }
            binding?.additionalInfo?.setBackgroundColor(resources.getColor(R.color.grey, null))
            binding?.placeName?.text = geocoder.getFromLocation(currentLocation.latitude, currentLocation.longitude,1)[0].locality
            binding?.humidity?.text=currentWeather.humidity
            binding?.temperature?.text=currentWeather.temp
            binding?.UV?.text=currentWeather.uvi
            binding?.feelTemperature?.text=currentWeather.feels_like
            binding?.pressure?.text=currentWeather.pressure
            binding?.weatherName?.text = currentWeather.weatherDescription
            binding?.wind?.text = currentWeather.wind
            Picasso.with(view.context).load(currentWeather.weatherPicture).into(binding?.weatherPic)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cancellationTokenSource.cancel()
        binding = null
        weatherFullInfoViewModel.clear()
    }


    @SuppressLint("MissingPermission")
    private fun requestLocation(){
        val currentLocationTask: Task<Location> = fusedLocationClient.getCurrentLocation(PRIORITY_LOW_POWER, cancellationTokenSource.token)
        currentLocationTask.addOnCompleteListener { task: Task<Location> ->
            if(task.isSuccessful){
                currentLocation = task.result
                weatherFullInfoViewModel.getWeather(
                    currentLocation.latitude, currentLocation.longitude,
                    Locale.getDefault().country
                )
                Log.d("Location", "Getting location success ${task.result}")
            }else{
                Log.d("Location", "Getting location failed ${task.exception}")
            }
        }
    }

    private fun initRefresh(){
        val swipeRefresh = binding?.pullToRefresh!!
        swipeRefresh.setOnRefreshListener {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            swipeRefresh.isRefreshing=false
        }

    }
}