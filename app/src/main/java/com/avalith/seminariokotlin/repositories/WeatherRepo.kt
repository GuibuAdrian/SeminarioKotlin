package com.avalith.seminariokotlin.repositories

import com.avalith.seminariokotlin.service.RetrofitClient
import com.avalith.seminariokotlin.service.WeatherService

class WeatherRepo {
    private val retrofitService: WeatherService by lazy { RetrofitClient.builderRetrofit()
        .create(WeatherService::class.java) }

    fun getWeather(location: String) = retrofitService.getWeather(
        "177a01b276194098b0b130402230602",
        location)
}