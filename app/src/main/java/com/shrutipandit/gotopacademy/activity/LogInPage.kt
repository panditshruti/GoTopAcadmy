package com.shrutipandit.gotopacademy.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shrutipandit.gotopacademy.databinding.ActivityLogInPageBinding

class LogInPage : AppCompatActivity() {
    private lateinit var binding: ActivityLogInPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}