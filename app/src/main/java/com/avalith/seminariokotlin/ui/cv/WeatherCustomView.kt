package com.avalith.seminariokotlin.ui.cv

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.avalith.seminariokotlin.databinding.CustomviewWeatherBinding
import com.avalith.seminariokotlin.model.Weather
import com.squareup.picasso.Picasso

class WeatherCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
): ConstraintLayout(context, attrs, defStyleAttrs){

    private val binding: CustomviewWeatherBinding = CustomviewWeatherBinding.inflate(LayoutInflater.from(context), this, true)

    fun setWeather(weather: Weather) {
        val temp = weather.current.temp_c.toInt()
        val feel = weather.current.feelslike_c.toInt()
        val location = weather.location.name
        val url = "https:${weather.current.condition.icon}"

        binding.tempTextView.text = "Temp\n$temp ºC"
        binding.feelTextView.text = "ST\n$feel ºC"
        binding.locationTextView.text = "Ubicacion:\n$location"

        Picasso.get().load(url).into(binding.image)
    }
}