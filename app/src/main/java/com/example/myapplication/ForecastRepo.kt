package com.example.myapplication

import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class ForecastRepo {
    private val _weeklyForecast = MutableLiveData<List<DailyForeCast>>()
    val weeklyForecast = _weeklyForecast

    fun loadForecast(zipCode:String){
        val randomValues=List(7){ Random.nextFloat().rem(100)*100 }
        val forecastItems=randomValues.map { DailyForeCast(temp=it,desc="a good day") }
        _weeklyForecast.value=forecastItems
    }
}