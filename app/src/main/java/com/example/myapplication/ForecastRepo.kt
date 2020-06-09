package com.example.myapplication

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.api.CurrentWeather
import com.example.myapplication.api.WeeklyForecast
import com.example.myapplication.api.createOpenWeatherMapService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class ForecastRepo {
    private val _weeklyForecast = MutableLiveData<WeeklyForecast>()
    val weeklyForecast = _weeklyForecast

    private val _currentWeather= MutableLiveData<CurrentWeather>()
    val currentWeather=_currentWeather

   fun loadWeeklyForecast(zipCode:String){
        val call= createOpenWeatherMapService().currentWeather(zipCode,BuildConfig.OPEN_WEATHER_MAP_API_KEY)
       call.enqueue(object :Callback<CurrentWeather>{
           override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
               Log.e(ForecastRepo::class.java.simpleName,"err loading current weather",t)
           }

           override fun onResponse(call: Call<CurrentWeather>, response: Response<CurrentWeather>) {
               val weather=response.body()
               if(weather!=null){
                   val forecastCall= createOpenWeatherMapService().sevenDayForecast(
                       lat = weather.coord.lat,
                       lon = weather.coord.lon,
                       exclude = "current,minutely,hourly",
                       appid = BuildConfig.OPEN_WEATHER_MAP_API_KEY
                   )
                   forecastCall.enqueue(object :Callback<WeeklyForecast>{
                       override fun onFailure(call: Call<WeeklyForecast>, t: Throwable) {
                           Log.e(ForecastRepo::class.java.simpleName,"err loading weekly weather",t)
                       }

                       override fun onResponse(
                           call: Call<WeeklyForecast>,
                           response: Response<WeeklyForecast>
                       ) {
                           val weelyForecast=response.body()
                           if(weelyForecast!=null) _weeklyForecast.value=weelyForecast
                       }
                   })
               }
           }
       })
    }

    fun loadCurrentForecast(zipCode: String) {
        val call= createOpenWeatherMapService().currentWeather(zipCode,BuildConfig.OPEN_WEATHER_MAP_API_KEY)
        call.enqueue(object : Callback<CurrentWeather>{
            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(ForecastRepo::class.java.simpleName,"err loading current weather",t)
            }

            override fun onResponse(
                call: Call<CurrentWeather>,
                response: Response<CurrentWeather>
            ) {
                val weather=response.body()
                if(weather!=null){
                    _currentWeather.value=weather
                }
            }

        })
    }
}