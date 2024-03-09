package com.shrutipandit.gotopacademy

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
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
    fun getCurrentUserProfile(callback: ProfileCallback) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            firebaseDatabase.child("users").child(userId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val name = snapshot.child("name").getValue(String::class.java)
                        val fatherName = snapshot.child("fatherName").getValue(String::class.java)
                        val mobNo = snapshot.child("mobNo").getValue(String::class.java)
                        val address = snapshot.child("address").getValue(String::class.java)
                        val email = snapshot.child("email").getValue(String::class.java)

                        if (name != null && fatherName != null && mobNo !=    null && address != null &&  email != null) {
                            val profile =
                                address?.let { Profile(name, fatherName, mobNo, address,email,"") } // Replace "" with the appropriate field from your Profile class
                            if (profile != null) {
                                callback.onSuccess(profile)
                            }
                        } else {
                            callback.onFailure(Exception("Incomplete profile data"))
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        callback.onFailure(error.toException())
                    }
                })
        } else {
            callback.onFailure(Exception("User not logged in"))
        }
    }

    interface Callback {
        fun onSuccess()
        fun onFailure(exception: Exception)
    }
    interface ProfileCallback {
        fun onSuccess(profile: Profile)
        fun onFailure(exception: Exception)
    }
}