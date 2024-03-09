package com.shrutipandit.gotopacademy.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.shrutipandit.gotopacademy.R
import com.shrutipandit.gotopacademy.UserRepository
import com.shrutipandit.gotopacademy.activity.MainActivity
import com.shrutipandit.gotopacademy.databinding.FragmentLoginBinding
import com.shrutipandit.gotopacademy.databinding.FragmentSignUpBinding
import com.shrutipandit.gotopacademy.db.Profile
import com.shrutipandit.gotopacademy.viewmodel.AuthViewModel
import com.shrutipandit.gotopacademy.viewmodel.AuthViewModelFactory

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private  var _binding: FragmentSignUpBinding?=null
    private val binding get() = _binding!!

    private lateinit var authViewModel: AuthViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      _binding = FragmentSignUpBinding.bind(view)

      val userRepository = UserRepository()
      val viewModelFactory = AuthViewModelFactory(userRepository)
      authViewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]



      binding.apply {
          signUpBtn.setOnClickListener {
              authViewModel.registerUser(
                  email = emailSignUp.text.toString().trim(),
                  password = createPasswordSignUp.text.toString(),
                  profile = Profile(nameSignUp.text.toString().trim(),fatherNameSignUp.text.toString().trim(),mobNoSignUp.text.toString().trim(),addressSignUp.text.toString().trim(),emailSignUp.text.toString().trim(),""),
                  callback = object : UserRepository.Callback {
                      override fun onSuccess() {
                          val intent = Intent(requireContext(), MainActivity::class.java)
                          startActivity(intent)
                          requireActivity().finish()
                      }

                      override fun onFailure(exception: Exception) {
                          Toast.makeText(requireContext(), exception.message.toString(), Toast.LENGTH_SHORT).show()
                      }
                  }
              )

          }

      }


    }

    override fun onDestroyView() {
      super.onDestroyView()
      _binding = null
    }
  }

