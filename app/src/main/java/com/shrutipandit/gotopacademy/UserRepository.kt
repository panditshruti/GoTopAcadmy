package com.shrutipandit.gotopacademy

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.shrutipandit.gotopacademy.db.Profile

class UserRepository {

    private val auth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance().reference

    fun registerUser(email: String, password: String, profile: Profile, callback: Callback) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                val userId = authResult.user?.uid
                if (userId != null) {
                    firebaseDatabase.child("users")
                        .child(userId)
                        .setValue(profile)
                        .addOnSuccessListener {
                            callback.onSuccess()
                        }
                        .addOnFailureListener { exception ->
                            callback.onFailure(exception)
                        }
                }
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception)
            }
    }


    fun signInUser(email: String, password: String, callback: Callback) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                callback.onSuccess()
            }
            .addOnFailureListener { exception ->
                callback.onFailure(exception)
            }
    }

    fun isUserAlreadyLogin(): Boolean {
        return auth.currentUser == null
    }

    fun signOutUser(callback: Callback) {
        auth.signOut()
        callback.onSuccess()
    }

    fun resetPassword(email: String, callback: Callback) {
        auth.sendPasswordResetEmail(email).addOnSuccessListener {

            callback.onSuccess()
        }
            .addOnFailureListener {
                callback.onFailure(it)
            }

    }


    interface Callback {
        fun onSuccess()
        fun onFailure(exception: Exception)
    }
}
