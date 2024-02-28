package com.shrutipandit.gotopacademy.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.shrutipandit.gotopacademy.databinding.ActivityProfileBinding
import com.squareup.picasso.Picasso

class Profile : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val UPDATE_PROFILE_REQUEST = 1
    private var isEditing = false
    private var selectedImageUri: Uri? = null

    private val galleryLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val selectedImage: Uri? = result.data?.data
                if (selectedImage != null) {
                    // Save the selected image URI
                    selectedImageUri = selectedImage

                    // Load the selected image into ImageView using Picasso
                    Picasso.get().load(selectedImage).into(binding.imagechoose)
                }
            }
        }

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

        if (!isEditing) {
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
        }

        binding.imagechoose.setOnClickListener {
            openGallery()
        }

        binding.editBtn.setOnClickListener {
            binding.name.text = ""
            binding.fatherName.text = ""
            binding.email.text = ""
            binding.call.text = ""
            binding.dob.text = ""
            binding.pincode.text = ""
            binding.village.text = ""
            binding.state.text = ""
            binding.country.text = ""
            binding.city.text = ""

            val intent = Intent(this, UpdateProfile::class.java)
            startActivityForResult(intent, UPDATE_PROFILE_REQUEST)
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(galleryIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UPDATE_PROFILE_REQUEST && resultCode == RESULT_OK) {
            val updatedName = data?.getStringExtra("UpdatedName")
            val updatedFatherName = data?.getStringExtra("UpdatedFatherName")
            val updatedEmail = data?.getStringExtra("UpdatedEmail")
            val updatedPhone = data?.getStringExtra("updatedPhone")
            val updatedDob = data?.getStringExtra("updatedDob")
            val updatedPincode = data?.getStringExtra("updatedPincode")
            val updatedVillage = data?.getStringExtra("updatedVillage")
            val updatedCountry = data?.getStringExtra("updatedCountry")
            val updatedState = data?.getStringExtra("updatedState")
            val updatedCity = data?.getStringExtra("updatedCity")

            runOnUiThread {
                binding.name.text = updatedName
                binding.fatherName.text = updatedFatherName
                binding.email.text = updatedEmail
                binding.call.text = updatedPhone
                binding.dob.text = updatedDob
                binding.pincode.text = updatedPincode
                binding.village.text = updatedVillage
                binding.state.text = updatedState
                binding.city.text = updatedCity
                binding.country.text = updatedCountry
                binding.state.text = updatedState
            }

            isEditing = true
        }
    }
}
