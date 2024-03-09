package com.shrutipandit.gotopacademy.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.shrutipandit.gotopacademy.R
import com.shrutipandit.gotopacademy.UserRepository
import com.shrutipandit.gotopacademy.databinding.FragmentProfileBinding
import com.shrutipandit.gotopacademy.db.Profile
import com.shrutipandit.gotopacademy.viewmodel.AuthViewModel
import com.shrutipandit.gotopacademy.viewmodel.AuthViewModelFactory

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var authViewModel: AuthViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        val userRepository = UserRepository()
        val viewModelFactory = AuthViewModelFactory(userRepository)
        authViewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]

        authViewModel.getCurrentUserProfile(callback = object : UserRepository.ProfileCallback {
            override fun onSuccess(profile: Profile) {
                Log.d("ProfileFragment", "Profile fetched successfully: $profile")
                binding.apply {
                    nameProfileTv.text = profile.name
                    fatherNameProfileTv.text = profile.fatherName
                    mobileProfileTv.text = profile.mobNo
                    addressProfileTv.text = profile.address
                    emailProfileTv.text = profile.email
                }
            }

            override fun onFailure(exception: Exception) {
                Log.e("ProfileFragment", "Failed to fetch profile: ${exception.message}")
                Toast.makeText(requireContext(), exception.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
