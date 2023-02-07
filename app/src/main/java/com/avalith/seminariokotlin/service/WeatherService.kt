package com.avalith.seminariokotlin.service

import com.avalith.seminariokotlin.model.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("current.json")
    fun getWeather( @Query("key") key: String,
                    @Query("q") location: String): Call<Weather>
}