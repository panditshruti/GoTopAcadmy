package com.shrutipandit.gotopacademy.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.shrutipandit.gotopacademy.R
import com.shrutipandit.gotopacademy.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {
    private lateinit var binding: FragmentEditProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditProfileBinding.bind(view)

        // Populate the fields with existing data from SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("MY_PRE", Context.MODE_PRIVATE)
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

            // Create the intent with updated data
            val updatedIntent = Bundle().apply {
                putString("UpdatedName", binding.name.text.toString())
                putString("UpdatedFatherName", binding.fatherName.text.toString())
                putString("UpdatedEmail", binding.email.text.toString())
                putString("updatedPhone", binding.phone.text.toString())
                putString("updatedDob", binding.dob.text.toString())
                putString("updatedPincode", binding.pinCode.text.toString())
                putString("updatedVillage", binding.village.text.toString())
                putString("updatedCountry", binding.country.text.toString())
                putString("updatedState", binding.state.text.toString())
                putString("updatedCity", binding.city.text.toString())
            }

            // Navigate to the ProfileFragment after saving the data
            findNavController().navigate(
                R.id.action_editProfileFragment_to_homeFragment,
                updatedIntent
            )

            // Set the result and finish the activity
            requireActivity().setResult(Activity.RESULT_OK)
            requireActivity().finish()
        }
    }
}
