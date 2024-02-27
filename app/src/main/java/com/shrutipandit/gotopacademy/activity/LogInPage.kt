package com.shrutipandit.gotopacademy.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shrutipandit.gotopacademy.databinding.ActivityLogInPageBinding

class LogInPage : AppCompatActivity() {
    private lateinit var binding: ActivityLogInPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
        val mobbileNo = sharedPreferences.getString("MOBBILENO", "")
        val password = sharedPreferences.getString("PASSWORD", "")
        val conPassword = sharedPreferences.getString("CONPASSWORD", "")

        if (mobbileNo != "" && password != "" && conPassword != "") {
            val intent = Intent(this, EditProfile::class.java)
            startActivity(intent)
            finish()
        }

        binding.login.setOnClickListener {
            val edmobbileNo = binding.mobbileNo.text.toString()
            val edpassword = binding.password.text.toString()
            val edconpassword = binding.conformPassword.text.toString()

            // Check if entered values are not empty
            if (edmobbileNo.isNotEmpty() && edpassword.isNotEmpty() && edconpassword.isNotEmpty()) {

                // Check if passwords match
                if (edpassword == edconpassword) {
                    val editor = sharedPreferences.edit()
                    editor.putString("MOBBILENO", edmobbileNo)
                    editor.putString("PASSWORD", edpassword)
                    editor.putString("CONPASSWORD", edconpassword)
                    editor.apply()

                    // Redirect to the next activity
                    val intents = Intent(this, EditProfile::class.java)
                    startActivity(intents)
                    finish()
                } else {
                    // Show an error message or handle it as needed
                    binding.conformPassword.error = "Passwords do not match"
                }
            } else {
                // Show an error message or handle it as needed
            }
        }
    }
}
