package com.example.shrutiPandit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shrutiPandit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.form.setOnClickListener {

            val intent = Intent(this,Form::class.java)
            startActivity(intent)
        }

        binding.result.setOnClickListener {

            val intent = Intent(this,Result::class.java)
            startActivity(intent)
        }

    binding.notice.setOnClickListener {
        val intent = Intent(this, Notice::class.java)
        startActivity(intent)
    }

        binding.about.setOnClickListener {

            val intent = Intent(this,About::class.java)
            startActivity(intent)
        }



    }
}

