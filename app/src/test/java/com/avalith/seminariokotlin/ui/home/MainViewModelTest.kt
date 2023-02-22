package com.avalith.seminariokotlin.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.avalith.seminariokotlin.model.Weather
import com.avalith.seminariokotlin.repositories.WeatherRepo
import retrofit2.Call
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Callback
import retrofit2.Response

internal class MainViewModelTest{
    private lateinit var mainViewModel: MainViewModel
    private lateinit var weatherRepo: WeatherRepo
    private lateinit var call: Call<Weather>

    private var liveData = MutableLiveData<Weather?>()
    private var weather: Weather?= null

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        mainViewModel = MainViewModel()
        weatherRepo = WeatherRepo()
        call = weatherRepo.getWeather("Cordoba")
        println("@Before")
        getWeather()
        observeToLiveData()
    }

    private fun observeToLiveData() {
        liveData.observeForever{ weather = it }
        println("@Subscribed")
    }

    private fun getWeather() {
        call.enqueue(object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                liveData.value = response.body()
            }

            override fun onFailure(call: Call<Weather>, t:Throwable) {
                liveData.value = null
            }
        })
    }

    @Test
    fun `get weather null`() {
        assertNull(weather)
        println("@Test -> es null")
    }

    @Test
    fun `get weather not null`() {
        //Given
        val resultadoEsperado = "Cordoba"

        //When
        getValue(liveData)


        //Then
        assertEquals(resultadoEsperado, liveData.getOrAwaitValue()?.location?.name)
        println("@Test -> $weather")
    }
}