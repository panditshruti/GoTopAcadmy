package com.shrutipandit.gotopacademy.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.shrutipandit.gotopacademy.R
import com.shrutipandit.gotopacademy.UserRepository
import com.shrutipandit.gotopacademy.activity.MainActivity
import com.shrutipandit.gotopacademy.databinding.FragmentLoginBinding
import com.shrutipandit.gotopacademy.viewmodel.AuthViewModel
import com.shrutipandit.gotopacademy.viewmodel.AuthViewModelFactory


class LoginFragment : Fragment(R.layout.fragment_login) {
    private  var _binding:FragmentLoginBinding?=null
    private val binding get() = _binding!!

    private lateinit var authViewModel: AuthViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        val userRepository = UserRepository()
        val viewModelFactory = AuthViewModelFactory(userRepository)
        authViewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]



        binding.apply {
            login.setOnClickListener {
                authViewModel.signInUser(
                    email = emailIdLogin.text.toString().trim(),
                    password = passwordLogin.text.toString(),
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
            signUpLogin.setOnClickListener {
                val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
                findNavController().navigate(action)

            }

            forgetPassTvLogin.setOnClickListener {
               val callback = object : UserRepository.Callback {
                    override fun onSuccess() {
                        Toast.makeText(requireContext(), "check your mail id", Toast.LENGTH_SHORT).show()

                    }

                    override fun onFailure(exception: Exception) {
                        Toast.makeText(requireContext(), exception.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                authViewModel.resetPassword(emailIdLogin.text.toString().trim(),callback)

            }
        }




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
