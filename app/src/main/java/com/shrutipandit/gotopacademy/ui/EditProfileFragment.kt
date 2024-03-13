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
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var authViewModel: AuthViewModel
    private lateinit var userRepository: UserRepository
    private lateinit var db: FirebaseDatabase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEditProfileBinding.bind(view)

        // Correctly initialize FirebaseDatabase
        db = FirebaseDatabase.getInstance()

        userRepository = UserRepository()
        val viewModelFactory = AuthViewModelFactory(userRepository)
        authViewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]
        authViewModel.getCurrentUserProfile(callback = object : UserRepository.ProfileCallback {
            override fun onSuccess(profile: Profile) {
                Log.d("ProfileFragment", "Profile fetched successfully: $profile")
                binding.apply {
                    nameEditProfile.setText(profile.name)
                    fatherNameEditProfile.setText(profile.fatherName)
                    emailEditProfile.setText(profile.email)
                    phoneEditProfile.setText(profile.mobNo)
                    addressEditProfile.setText(profile.address)

                }
            }

            override fun onFailure(exception: Exception) {
                Log.e("MyTag", "Failed to fetch Edit profile: ${exception.message}")
                Toast.makeText(requireContext(), exception.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        })
        binding.apply {
            submitbtn.setOnClickListener {
                authViewModel.updateUserProfile(
                    profile = Profile(
                        nameEditProfile.text.toString(),
                        fatherNameEditProfile.text.toString(),
                        phoneEditProfile.text.toString(),
                        addressEditProfile.text.toString(),
                        emailEditProfile.text.toString(),
                        ""
                    ),
                    callback = object : UserRepository.Callback {
                        override fun onSuccess() {
                            Toast.makeText(requireContext(), "Updated", Toast.LENGTH_SHORT).show()
                            findNavController().popBackStack()
                        }

                        override fun onFailure(exception: Exception) {
                            Toast.makeText(
                                requireContext(),
                                exception.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
