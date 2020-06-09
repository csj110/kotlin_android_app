package com.example.myapplication.api

import com.squareup.moshi.Json

data class Forecast(val temp:Float)

data class Coordinates(val lat:Float,val lng:Float)

data class CurrentWeather(
    val name:String,
    val coord:Coordinates,
    @field:Json(name="main")  val forecast: Forecast
)