package com.shrutipandit.gotopacademy.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shrutipandit.gotopacademy.R
import com.shrutipandit.gotopacademy.databinding.ActivityProfileBinding

class Profile : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("NAME", "")
        val fatherName = sharedPreferences.getString("FATHER", "")
        val phone = sharedPreferences.getString("PHONE", "")
        val email = sharedPreferences.getString("EMAIL", "")
        val dob = sharedPreferences.getString("DOB", "")
        val pinCode = sharedPreferences.getString("PINCODE", "")
        val village = sharedPreferences.getString("VILLAGE", "")
        val state = sharedPreferences.getString("STATE", "")
        val country = sharedPreferences.getString("COUNTRY", "")
        val city = sharedPreferences.getString("CITY", "")
        val isMale = sharedPreferences.getBoolean("MALE", false)
        val femaleMale = sharedPreferences.getBoolean("FEMALE", false)


        binding.name.text = name.toString()
        binding.fatherName.text = fatherName.toString()
        binding.email.text = email.toString()
        binding.call.text = phone.toString()
        binding.dob.text = dob.toString()
        binding.pincode.text = pinCode.toString()
        binding.village.text = village.toString()
        binding.state.text = state.toString()
        binding.country.text = country.toString()
        binding.city.text = city.toString()
        binding.city.text = city.toString()

    }
}