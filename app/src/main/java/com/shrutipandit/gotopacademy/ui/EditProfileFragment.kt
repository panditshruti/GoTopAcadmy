package com.shrutipandit.gotopacademy.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.FirebaseDatabase
import com.shrutipandit.gotopacademy.R
import com.shrutipandit.gotopacademy.UserRepository
import com.shrutipandit.gotopacademy.databinding.FragmentEditProfileBinding
import com.shrutipandit.gotopacademy.db.Profile
import com.shrutipandit.gotopacademy.viewmodel.AuthViewModel
import com.shrutipandit.gotopacademy.viewmodel.AuthViewModelFactory

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {
    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var userRepository: UserRepository
    private lateinit var db: FirebaseDatabase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditProfileBinding.bind(view)

        // Correctly initialize FirebaseDatabase
        db = FirebaseDatabase.getInstance()

        userRepository = UserRepository()
        val viewModelFactory = AuthViewModelFactory(userRepository)
        authViewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]

        binding.submitbtn.setOnClickListener {
            // Get updated profile details from the UI
            val updatedProfile = getUpdatedProfile()

            // Update the profile in Firebase
            authViewModel.updateUserProfile(updatedProfile, object : UserRepository.Callback {
                override fun onSuccess() {
                    // Profile updated successfully
                    Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp() // Navigate back to ProfileFragment
                }

                override fun onFailure(exception: Exception) {
                    // Handle failure
                    Log.e("EditProfileFragment", "Failed to update profile: ${exception.message}")
                    Toast.makeText(requireContext(), "Failed to update profile", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun getUpdatedProfile(): Profile {
        // Implement logic to fetch updated profile details from the UI components
        // For example:
        val name = binding.name.text.toString()
        val fatherName = binding.fatherName.text.toString()
        val emailid = binding.email.text.toString()
        val mobile = binding.phone.text.toString()
        val address = binding.address.text.toString()
        // ... and so on

        return Profile(name, fatherName, mobile, address, emailid, "")
    }
}
