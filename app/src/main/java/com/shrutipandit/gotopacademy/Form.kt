package com.shrutipandit.gotopacademy


import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.shrutipandit.gotopacademy.databinding.ActivityFormBinding
import com.shrutipandit.gotopacademy.utils.Validate
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Form : AppCompatActivity(){
    private lateinit var binding: ActivityFormBinding
    private lateinit var examAdapter : ArrayAdapter<String>
    private lateinit var examTypeAdapter : ArrayAdapter<String>
    private lateinit var database: DatabaseReference
    private lateinit var examArrayCategories:Array<String>
    private lateinit var examTypeSelected:String
    private lateinit var examCategorySelected:String
    private lateinit var optionSelected:ArrayList<String>
    private lateinit var optionSelectedAdapter:ArrayAdapter<String>

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri = uri
        isImageSelected = true
        Picasso.get().load(uri).into(binding.imagechoose)
    }
    private var selectedImageUri: Uri? = null
    private var isImageSelected = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = FirebaseDatabase.getInstance().reference.child("Student Details")

        binding.imagechoose.setOnClickListener {
            openFilePicker(1)
        }
        binding.submitbtn.setOnClickListener {
            saveFileToDatabase()
        }
        binding.addBtnForm.setOnClickListener {
           optionSelected.add(examCategorySelected + examTypeSelected)
            optionSelectedAdapter.notifyDataSetChanged()
            Toast.makeText(this, "working", Toast.LENGTH_SHORT).show()
        }


        optionSelected = arrayListOf()
        optionSelectedAdapter = ArrayAdapter(this@Form,android.R.layout.simple_list_item_1,optionSelected)
        binding.optionSelectedListviewForm.adapter = optionSelectedAdapter
        examArrayCategories  = resources.getStringArray(R.array.exam_category)
        examAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,examArrayCategories)
        examAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categoryExamForm.adapter = examAdapter

        binding.categoryExamForm.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
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

            }

        }

    }



    private fun updateStateSpinner(selectedCategories:String){
        val stateArrayId = when(selectedCategories){
            "Scc Exams"->{R.array.ssc_exam_category}
            "Railway Exams"->{R.array.railway_exam_category}
            "Defence Exams"->{R.array.defence_exam_category}
            "Police Exams"->{R.array.police_exam_category}
            "Civil Services Exams"->{R.array.civil_exam_category}
            "Banking Exams"->{R.array.banking_exam_category}
            "Entrance Exams"->{R.array.entrance_exam_category}
            "Current Affairs Exams"->{R.array.current_affairs_exam_category}
            else -> {R.array.school_exam_category}
        }
        
        val examType :Array<String> = resources.getStringArray(stateArrayId)
        examTypeAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,examType)
        examTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.examTypeSpinnerForm.adapter = examTypeAdapter

        binding.examTypeSpinnerForm.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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
                TODO("Not yet implemented")
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
        val name = binding.name.text.toString()
        val phone = binding.phone.text.toString()
        val email = binding.email.text.toString()
        val address = binding.address.text.toString()


        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty()) {
            // Show a required message if any text field is empty
            binding.name.error = "Require Name"
            binding.phone.error = "Require Phone-No."
            binding.email.error = "Require email I'd"
            binding.address.error = "Require Address"
            return
        }

        if(!Validate.isEmailValid(email)){
            binding.email.error = "Invalid Email"
            return
        }

        if(!Validate.isMobileNumberValid(phone)){
            binding.phone.error = "Phone No Invalid"
            return
        }

        if (selectedImageUri == null || !isImageSelected ) {
            // Show a required message if any image is not selected
            Toast.makeText(this, "Both images are required", Toast.LENGTH_SHORT).show()
            return
        }
         if (optionSelected.isEmpty()){
             Toast.makeText(this, "At least one option should be selected", Toast.LENGTH_SHORT).show()
             return
         }

        if (selectedImageUri != null && isImageSelected  &&
            name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty() && address.isNotEmpty()
        ) {
            val currentDate = SimpleDateFormat("yyyy_MM_dd", Locale.getDefault()).format(Date())
            val entryKey = database.push().key

            entryKey?.let {
                database.child(entryKey).child("name").setValue(name)
                database.child(entryKey).child("phone No-").setValue(phone)
                database.child(entryKey).child("email").setValue(email)
                database.child(entryKey).child("address").setValue(address)
                database.child(entryKey).child("schoolExam").setValue(optionSelected)
               // database.child(entryKey).child("sscExam").setValue(sscExam)

                database.child(entryKey).child("CurrentDate").setValue(currentDate)

                uploadImageToStorage(selectedImageUri, 1,  entryKey)


                binding.name.text?.clear()
                binding.phone.text?.clear()
                binding.email.text?.clear()
                binding.address.text?.clear()


               // binding.imagechoose.setImageDrawable(R.drawable.ic_menu_gallery)

                isImageSelected = false



                Toast.makeText(this, "data Uploded", Toast.LENGTH_SHORT).show()
            }
        } else {
        }
    }
    private fun uploadImageToStorage(uri: Uri?, requestCode: Int, entryKey: String) {
        uri?.let {
            val storageReference =
                FirebaseStorage.getInstance().reference.child("images/${System.currentTimeMillis()}_${requestCode}.jpg")

            storageReference.putFile(uri)
                .addOnSuccessListener {
                    storageReference.downloadUrl.addOnSuccessListener { downloadUri ->
                        saveImageUrlToDatabase(downloadUri.toString(), requestCode, entryKey)
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Image $requestCode Upload Failed: ${exception.message}", Toast.LENGTH_SHORT).show()

                }
        }
    }

    private fun saveImageUrlToDatabase(downloadUrl: String, requestCode: Int, entryKey: String) {
        val uriKey = "uri$requestCode"
        database.child(entryKey).child(uriKey).setValue(downloadUrl)

    }


}