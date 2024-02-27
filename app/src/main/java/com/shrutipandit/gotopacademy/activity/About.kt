package com.shrutipandit.gotopacademy.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shrutipandit.gotopacademy.databinding.ActivityAboutBinding

class About : AppCompatActivity() {
    private lateinit var binding:ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}