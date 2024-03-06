package com.shrutipandit.gotopacademy.viewmodel


// AuthViewModel.kt
import androidx.lifecycle.ViewModel
import com.shrutipandit.gotopacademy.UserRepository
import com.shrutipandit.gotopacademy.db.Profile

class AuthViewModel(private val repository: UserRepository) : ViewModel() {

    fun registerUser(email: String, password: String, profile: Profile, callback: UserRepository.Callback) {
        repository.registerUser(email, password, profile, callback)
    }
    fun signInUser(email: String, password: String, callback: UserRepository.Callback) {
        repository.signInUser(email, password, callback)
    }

    fun isUserAlreadyLogin():Boolean{
       return repository.isUserAlreadyLogin()
    }

    fun  signOutUser(callback: UserRepository.Callback){
        repository.signOutUser(callback)
    }
  fun  resetPassword(email:String,callback: UserRepository.Callback){
        repository.resetPassword(email,callback)
    }

}
