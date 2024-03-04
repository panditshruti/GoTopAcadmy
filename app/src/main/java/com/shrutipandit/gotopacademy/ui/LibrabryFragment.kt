package com.shrutipandit.gotopacademy.ui

import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.shrutipandit.gotopacademy.R
import com.shrutipandit.gotopacademy.databinding.FragmentLibrabryBinding
import com.shrutipandit.gotopacademy.utils.Validate
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LibrabryFragment : Fragment(R.layout.fragment_librabry) {
    private lateinit var binding:FragmentLibrabryBinding
        private lateinit var examAdapter: ArrayAdapter<String>
        private lateinit var examTypeAdapter: ArrayAdapter<String>
        private lateinit var database: DatabaseReference
        private lateinit var examArrayCategories: Array<String>
        private lateinit var examTypeSelected: String
        private lateinit var examCategorySelected: String
        private lateinit var selectedOptionsText: StringBuilder
        private lateinit var progressDialog: ProgressDialog

        private val pickImage =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                selectedImageUri = uri
                isImageSelected = true
                Picasso.get().load(uri).into(binding.imageChoose)
            }

        private var selectedImageUri: Uri? = null
        private var isImageSelected = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLibrabryBinding.bind(view)


            database = FirebaseDatabase.getInstance().reference.child("Library Student Details")

            // Initialize ProgressDialog
            progressDialog = ProgressDialog(requireContext())
            progressDialog.setMessage("Uploading file...")
            progressDialog.setCancelable(false)

            binding.imageChoose.setOnClickListener {
                openFilePicker(1)
            }

            binding.submitBtn.setOnClickListener {
                saveFileToDatabase()
            }

            binding.addBtnForm.setOnClickListener {
                val newItem = "$examCategorySelected ----> $examTypeSelected"
                selectedOptionsText.clear()
                selectedOptionsText.append(newItem)
                binding.optionSelectedTextviewForm.text = selectedOptionsText.toString()
                Toast.makeText(requireContext(), "Exam Added", Toast.LENGTH_SHORT).show()
            }

            selectedOptionsText = StringBuilder()

            examArrayCategories = resources.getStringArray(R.array.library)
            examAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, examArrayCategories)
            examAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.categoryExamForm.adapter = examAdapter

            binding.categoryExamForm.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val selectedCategory = parent?.getItemAtPosition(position).toString()
                        examCategorySelected = selectedCategory
                        updateStateSpinner(selectedCategory)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        // Handle when nothing is selected
                    }
                }
        }

        private fun updateStateSpinner(selectedCategories: String) {
            val stateArrayId = when (selectedCategories) {

                "Library Time" -> R.array.library_time

                else -> R.array.clas
            }

            val examType: Array<String> = resources.getStringArray(stateArrayId)
            examTypeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, examType)
            examTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.examTypeSpinnerForm.adapter = examTypeAdapter

            binding.examTypeSpinnerForm.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val selectedExamType = parent?.getItemAtPosition(position).toString()
                        examTypeSelected = selectedExamType
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        // Handle when nothing is selected
                    }
                }
        }

        private fun openFilePicker(requestCode: Int) {
            val mimeType = "image/*"
            when (requestCode) {
                1 -> pickImage.launch(mimeType)
                else -> throw IllegalArgumentException("Unsupported request code: $requestCode")
            }
        }

        private fun saveFileToDatabase() {
            // Show ProgressDialog when saving file
            progressDialog.show()

            val name = binding.name.text.toString()
            val phone = binding.phone.text.toString()
            val email = binding.email.text.toString()
            val address = binding.address.text.toString()
            val optionTextview = binding.optionSelectedTextviewForm.text.toString()

            if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty()) {
                // Show a required message if any text field is empty
                binding.name.error = "Require Name"
                binding.phone.error = "Require Phone-No."
                binding.email.error = "Require email I'd"
                binding.address.error = "Require Address"

                // Dismiss ProgressDialog on failure
                progressDialog.dismiss()
                return
            }

            if (!Validate.isEmailValid(email)) {
                binding.email.error = "Invalid Email"

                // Dismiss ProgressDialog on failure
                progressDialog.dismiss()
                return
            }

            if (!Validate.isMobileNumberValid(phone)) {
                binding.phone.error = "Phone No Invalid"

                // Dismiss ProgressDialog on failure
                progressDialog.dismiss()
                return
            }

            if (selectedImageUri == null || !isImageSelected) {
                // Show a required message if any image is not selected
                Toast.makeText(requireContext(), " images are required", Toast.LENGTH_SHORT).show()

                // Dismiss ProgressDialog on failure
                progressDialog.dismiss()
                return
            }

            if (binding.optionSelectedTextviewForm.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Require Exam Option", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
                return
            }

            if (selectedImageUri != null && isImageSelected &&
                name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty() && address.isNotEmpty() && optionTextview.isNotEmpty()
            ) {
                val currentDate =
                    SimpleDateFormat("yyyy_MM_dd", Locale.getDefault()).format(Date())
                val entryKey = database.push().key

                entryKey?.let {
                    val storageReference =
                        FirebaseStorage.getInstance().reference
                            .child("images/${System.currentTimeMillis()}.jpg")

                    storageReference.putFile(selectedImageUri!!)
                        .addOnSuccessListener {
                            storageReference.downloadUrl.addOnSuccessListener { downloadUri ->
                                // Save image URL to database
                                saveImageUrlToDatabase(downloadUri.toString(), entryKey)

                                // Save other details to database
                                database.child(entryKey).child("name").setValue(name)
                                database.child(entryKey).child("phone No-").setValue(phone)
                                database.child(entryKey).child("email").setValue(email)
                                database.child(entryKey).child("address").setValue(address)
                                database.child(entryKey).child("schoolExam")
                                    .setValue(selectedOptionsText.toString())
                                database.child(entryKey).child("CurrentDate").setValue(currentDate)

                                // Hide ProgressDialog when upload is successful
                                progressDialog.dismiss()

                                // Reset form fields and image selection
                                binding.name.text?.clear()
                                binding.phone.text?.clear()
                                binding.email.text?.clear()
                                binding.address.text?.clear()
                                binding.optionSelectedTextviewForm.text = ""
                                binding.imageChoose.setImageResource(R.drawable.img2)
                                isImageSelected = false
                            }
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(
                                requireContext(),
                                "Image Upload Failed: ${exception.message}",
                                Toast.LENGTH_SHORT
                            ).show()

                            // Hide ProgressDialog on failure
                            progressDialog.dismiss()
                        }
                }
            } else {
                // Handle the case where some required fields are empty or image is not selected
                // Dismiss ProgressDialog on failure
                progressDialog.dismiss()
                Toast.makeText(requireContext(), "Require Exam Option", Toast.LENGTH_SHORT).show()
            }
        }

        private fun saveImageUrlToDatabase(downloadUrl: String, entryKey: String) {
            val uriKey = "uri1"
            database.child(entryKey).child(uriKey).setValue(downloadUrl)
        }
    }


