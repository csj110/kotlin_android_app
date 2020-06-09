package com.example.myapplication

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.api.CurrentWeather
import com.example.myapplication.api.createOpenWeatherMapService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class ForecastRepo {
    private val _weeklyForecast = MutableLiveData<List<DailyForeCast>>()
    val weeklyForecast = _weeklyForecast

    private val _currentForecast= MutableLiveData<DailyForeCast>()
    val currentForecast=_currentForecast

   fun loadWeeklyForecast(zipCode:String){
        val randomValues=List(7){ Random.nextFloat().rem(100)*100 }
        val forecastItems=randomValues.map { DailyForeCast(temp=it,desc="a good day") }
        _weeklyForecast.value=forecastItems
    }

    fun loadCurentForecast(zipCode: String){
        val temp= Random.nextFloat().rem(100)*100
        val dailyForeCast=DailyForeCast(temp,"a nice good day")
        _currentForecast.value=dailyForeCast
    fun loadCurrentForecastByNetWork(zipCode: String){
        val call= createOpenWeatherMapService().currentWeather(zipCode,"imperial","appid")
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
                    Log.e(ForecastRepo::class.java.simpleName,"data loaded need to be used")
                }
            }

        })
    }
}