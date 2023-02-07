package com.avalith.seminariokotlin.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.avalith.seminariokotlin.R
import com.avalith.seminariokotlin.databinding.ActivityMainBinding
import com.avalith.seminariokotlin.extensions.clearPrefs
import com.avalith.seminariokotlin.extensions.signOut
import com.avalith.seminariokotlin.model.Post
import com.avalith.seminariokotlin.model.Weather
import com.avalith.seminariokotlin.ui.adapter.PostAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    companion object {
        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, MainActivity::class.java))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getWeather()
        viewModel.getData()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        viewModel.dataLiveData.observe(this) { setAdapter(it) }
        viewModel.weatherLiveData.observe(this) { setWeather(it) }
        viewModel.errorLiveData.observe(this) { showMessage(it) }
    }

    private fun setAdapter(list: List<Post>) {
        binding.postlistrecyclerView.layoutManager = LinearLayoutManager(this)
        binding.postlistrecyclerView.adapter = PostAdapter(list)
    }

    private fun setWeather(weather: Weather?) {
        weather?.let { binding.cardView.setWeather(it) }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        signOut()
        clearPrefs()
    }
}