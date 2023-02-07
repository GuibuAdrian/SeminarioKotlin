package com.avalith.seminariokotlin.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avalith.seminariokotlin.model.Post
import com.avalith.seminariokotlin.model.Weather
import com.avalith.seminariokotlin.repositories.FirebaseRepo
import com.avalith.seminariokotlin.repositories.WeatherRepo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    val dataLiveData = MutableLiveData<List<Post>>()
    val weatherLiveData = MutableLiveData<Weather>()
    val errorLiveData = MutableLiveData<String>()
    private val weatherRepo = WeatherRepo()
    private val firebaseRepo = FirebaseRepo()

    fun getWeather() {
        val call: Call<Weather> = weatherRepo.getWeather()
        call.enqueue(object: Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                weatherLiveData.value = response.body()
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                errorLiveData.value = t.message
            }
        })
    }

    fun getData() {
        val postList = ArrayList<Post>()
        firebaseRepo.getPost().addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { data ->
                    data.getValue(Post::class.java)?.let {
                        postList.add(it)
                    }
                }
                dataLiveData.value = postList
            }

            override fun onCancelled(error: DatabaseError) {
                errorLiveData.value = error.message
            }

        })
    }
}