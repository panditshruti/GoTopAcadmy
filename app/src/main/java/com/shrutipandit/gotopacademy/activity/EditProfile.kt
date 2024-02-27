package com.shrutipandit.gotopacademy.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.shrutipandit.gotopacademy.R
import com.shrutipandit.gotopacademy.databinding.ActivityEditProfileBinding

class EditProfile : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
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

        // Populate fields with saved data
        binding.name.setText(name)
        binding.fatherName.setText(fatherName)
        binding.phone.setText(phone)
        binding.email.setText(email)
        binding.dob.setText(dob)
        binding.pinCode.setText(pinCode)
        binding.village.setText(village)
        binding.state.setText(state)
        binding.country.setText(country)
        binding.city.setText(city)
        binding.male.isChecked = isMale
        binding.female.isChecked = isMale

        if (name != "" && fatherName != ""  && phone != ""  && email != ""  && dob != ""  &&
            pinCode != "" && village != ""  && country != "" && state != "" && city != "" ) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.submitbtn.setOnClickListener {
            val enteredName = binding.name.text.toString()
            val enteredFatherName = binding.fatherName.text.toString()
            val enteredPhone = binding.phone.text.toString()
            val enteredEmail = binding.email.text.toString()
            val enteredDob = binding.dob.text.toString()
            val enteredPinCode = binding.pinCode.text.toString()
            val enteredVillage = binding.village.text.toString()
            val enteredCountry = binding.country.text.toString()
            val enteredState = binding.state.text.toString()
            val enteredCity = binding.city.text.toString()
            val isMaleChecked = binding.male.isChecked
            val female = binding.female.isChecked

            // Check if entered values are not empty
            if (enteredName.isNotEmpty() && enteredFatherName.isNotEmpty() && enteredPhone.isNotEmpty() &&
                enteredEmail.isNotEmpty() && enteredDob.isNotEmpty() && enteredPinCode.isNotEmpty() &&
                enteredVillage.isNotEmpty() && enteredCountry.isNotEmpty() && enteredState.isNotEmpty() &&
                enteredCity.isNotEmpty()) {

                // Store the entered values in shared preferences
                val editor = sharedPreferences.edit()
                editor.putString("NAME", enteredName)
                editor.putString("FATHER", enteredFatherName)
                editor.putString("EMAIL", enteredEmail)
                editor.putString("PHONE", enteredPhone)
                editor.putString("DOB", enteredDob)
                editor.putString("PINCODE", enteredPinCode)
                editor.putString("VILLAGE", enteredVillage)
                editor.putString("STATE", enteredState)
                editor.putString("COUNTRY", enteredCountry)
                editor.putString("CITY", enteredCity)
                editor.putBoolean("MALE", isMaleChecked)
                editor.putBoolean("FEMALE", isMaleChecked)
                editor.apply()

                // Redirect to the next activity
                val intents = Intent(this, MainActivity::class.java)
                startActivity(intents)
                finish()
            } else {
                // Show an error message or handle it as needed
                if (enteredName.isEmpty()) binding.name.error = "Required"
                if (enteredFatherName.isEmpty()) binding.fatherName.error = "Required"
                if (enteredEmail.isEmpty()) binding.email.error = "Required"
                if (enteredPhone.isEmpty()) binding.phone.error = "Required"
                if (enteredDob.isEmpty()) binding.dob.error = "Required"
                if (enteredPinCode.isEmpty()) binding.pinCode.error = "Required"
                if (enteredVillage.isEmpty()) binding.village.error = "Required"
                if (enteredState.isEmpty()) binding.state.error = "Required"
                if (enteredCountry.isEmpty()) binding.country.error = "Required"
                if (enteredCity.isEmpty()) binding.city.error = "Required"

            }
        }
    }
}
