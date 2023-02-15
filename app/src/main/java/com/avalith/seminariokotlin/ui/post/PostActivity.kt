package com.avalith.seminariokotlin.ui.post

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.avalith.seminariokotlin.R
import com.avalith.seminariokotlin.databinding.ActivityMainBinding
import com.avalith.seminariokotlin.databinding.ActivityPostBinding
import com.avalith.seminariokotlin.ui.home.MainActivity

class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding

    companion object {
        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, PostActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}