package com.shrutipandit.gotopacademy.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shrutipandit.gotopacademy.databinding.ActivityUpdateProfileBinding

class UpdateProfile : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Populate the fields with existing data from SharedPreferences
        val sharedPreferences = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
        binding.name.setText(sharedPreferences.getString("NAME", ""))
        binding.fatherName.setText(sharedPreferences.getString("FATHER", ""))
        binding.email.setText(sharedPreferences.getString("EMAIL", ""))
        binding.phone.setText(sharedPreferences.getString("PHONE", ""))
        binding.dob.setText(sharedPreferences.getString("DOB", ""))
        binding.pinCode.setText(sharedPreferences.getString("PINCODE", ""))
        binding.village.setText(sharedPreferences.getString("VILLAGE", ""))
        binding.state.setText(sharedPreferences.getString("STATE", ""))
        binding.country.setText(sharedPreferences.getString("COUNTRY", ""))
        binding.city.setText(sharedPreferences.getString("CITY", ""))

        binding.submitbtn.setOnClickListener {
            // Save the edited data to SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("NAME", binding.name.text.toString())
            editor.putString("FATHER", binding.fatherName.text.toString())
            editor.putString("EMAIL", binding.email.text.toString())
            editor.putString("PHONE", binding.phone.text.toString())
            editor.putString("DOB", binding.dob.text.toString())
            editor.putString("PINCODE", binding.pinCode.text.toString())
            editor.putString("VILLAGE", binding.village.text.toString())
            editor.putString("STATE", binding.state.text.toString())
            editor.putString("COUNTRY", binding.country.text.toString())
            editor.putString("CITY", binding.city.text.toString())
            editor.apply()

            // Return to the Profile activity with the updated data
            val intent = Intent()
            intent.putExtra("UpdatedName", binding.name.text.toString())
            intent.putExtra("UpdatedFatherName", binding.fatherName.text.toString())
            intent.putExtra("UpdatedEmail", binding.email.text.toString())
            intent.putExtra("updatedPhone", binding.phone.text.toString())
            intent.putExtra("updatedDob", binding.dob.text.toString())
            intent.putExtra("updatedPincode", binding.pinCode.text.toString())
            intent.putExtra("updatedVillage", binding.village.text.toString())
            intent.putExtra("updatedCountry", binding.country.text.toString())
            intent.putExtra("updatedState", binding.state.text.toString())
            intent.putExtra("updatedCity", binding.city.text.toString())

            setResult(RESULT_OK, intent)
            finish()
        }
    }
}
