package com.example.shrutiPandit

import android.R
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.shrutiPandit.databinding.ActivityFormBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.EnumSet
import java.util.Locale

class Form : AppCompatActivity() ,PaymentStatusListener{
    private lateinit var binding: ActivityFormBinding
    private lateinit var spinner: Spinner
    private lateinit var database: DatabaseReference

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri = uri
        isImageSelected = true
        Picasso.get().load(uri).into(binding.imagechoose)
    }
    private val pickImage2 = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri2 = uri
        isImageSelected2 = true
        Picasso.get().load(uri).into(binding.sspay)
    }
    private var selectedImageUri: Uri? = null
    private var isImageSelected = false
    private var selectedImageUri2: Uri? = null
    private var isImageSelected2 = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = FirebaseDatabase.getInstance().getReference().child("Student Details")

        binding.imagechoose.setOnClickListener {
            openFilePicker(1)
        }
        binding.sspay.setOnClickListener {
            openFilePicker(2)
        }
        binding.submitbtn.setOnClickListener {
            saveFileToDatabase()
        }

        binding.qrcode.setOnClickListener {
         paymentMode()

        }

        school()
        ssc()
        railway()
        defance()
        police()
        civilService()
        banking()
        entrance()
        currentAffairs()

    }
    fun school() {
        spinner = binding.schoolAcadmicExam
        val schoolexam = arrayOf(
            "class 1",
            "class 2",
            "class 3",
            "class 4",
            "class 5",
            "class 6",
            "class 7",
            "class 8",
            "class 9",
            "class 10",
            "class 11",
            "class 12",
            "None"
        )

        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, schoolexam)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        binding.schoolAcadmicExam.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCountry = schoolexam[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
    fun ssc(){
        spinner = binding.ssc
        val ssc = arrayOf(
            "SSC GD Cinstable",
            "SSC CGL",
            "SSC CHSL",
            "SSC MTS",
            "SSC Selection Post",
            "SSC CPO",
            "None"

        )

        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, ssc)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        binding.ssc.onItemSelectedListener = object : AdapterView.OnItemSelectedListener { override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedCountry = ssc[position]
        }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

    }
    fun railway(){
        spinner = binding.railway
        val railway = arrayOf(
            "RRB Group D",
            "RRB ALP",
            "RRB NTPC",
            "RRB JE",
            "RPF SI",
            "RPF Constable",
            "RPSF",
            "None"

        )

        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, railway)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        binding.railway.onItemSelectedListener = object : AdapterView.OnItemSelectedListener { override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedCountry = railway[position]
        }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

    }
    fun defance(){
        spinner = binding.defence
        val defance = arrayOf(
            "Indian Army GD Agniveer",
            "Indian Army Clerk Agniveer",
            "Indian Army Tradesman Agniveer",
            "Indian Army Technical Agniveer",
            "Indian Army Nursing Agniveer",
            "Indian Airforce Group X",
            "Indian Airforce Group Y",
            "Indian Airforce Group C",
            "Indian Airforce Agniveer",
            "Indian Navy Agniveer",
            "Indian Navy Tradesman Agniveer",
            "Indian Navy MR Agniveer",
            "Indian Navy SSR Agniveer",
            "Indian Coast Guard Navik GD",
            "Indian Coast Guard Assistant",
            "Indian Coast Guard Yantrik",
            "CRPF Constable",
            "CRPF Head Constable",
            "CRPF SI",
            "CRPF ASI",
            "CRPF Paramedical Staff",
            "CISF Head Constable",
            "CISF Constable",
            "CISF ASI",
            "CISF Tradesman",
            "SSB Head Constable",
            "SSB SI",
            "BSF RO",
            "BSF ASI",
            "ITBP Head Constable",
            "ITBP Constable",
            "ITBP Tradsman",
            "Assam Rifles",
            "NDA",
            "CDS",
            "None"

        )

        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, defance)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        binding.defence.onItemSelectedListener = object : AdapterView.OnItemSelectedListener { override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedCountry = defance[position]
        }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

    }
    fun police(){
        spinner = binding.police
        val police = arrayOf(
            "Bihar Police SI",
            "Bihar Police Constable",
            "Bihar Police Prohibition",
            "Bihar Police Prohibition Sub",
            "Bihar Police ASI Steno",
            "Bihar Police Fireman",
            "Bihar Police Forest Duard",
            "Bihar AMIN",
            "Bihar LRC Clerk",
            "Bihar Vidhan Sabha Security",
            "BSSC",
            "BSSC CGL",
            "BSSC Stenographer",
            "Delhi Police Constable",
            "Delhi Police Head Constable",
            "Delhi Police MTS",
            "Delhi Police Driver",
            "UP Police Constable",
            "UP Police ASI",
            "UP Police SI",
            "UP Police Fireman",
            "UP Police Driver",
            "None"


        )

        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, police)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        binding.police.onItemSelectedListener = object : AdapterView.OnItemSelectedListener { override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedCountry = police[position]
        }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

    }
    fun civilService(){
        spinner = binding.cicilServices
        val civilService = arrayOf(
            "UPSE Civil Services",
            "BPSC Exam",
            "BPSC Judicial Services",
            "BPSC CDPO",
            "BPSC Primary Teacher(1st to 5th)",
            "BPSC Upper Primary Teacher(6th to 8th)",
            "BPSC Secondry Teacher(9th to 10th)",
            "BPSC Senior Secondry Teacher(11th to 12th)",
            "Patna High Court Computer",
            "Patna High Court Stenographer",
            "Patna Civil Court Clerk",
            "Patna Civil Court peon",
            "Patna Civil Court Stenographer",
            "None"

        )

        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, civilService)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        binding.cicilServices.onItemSelectedListener = object : AdapterView.OnItemSelectedListener { override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedCountry = civilService[position]
        }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

    }
    fun banking(){
        spinner = binding.banking
        val banking = arrayOf(
            "SBI PO",
            "SBI Clerk",
            "IBPS PO",
            "IBPS Clerk",
            "RRB Office Assistant",
            "Central Bank Of India Sub Staff",
            "None"

        )

        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, banking)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        binding.banking.onItemSelectedListener = object : AdapterView.OnItemSelectedListener { override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedCountry = banking[position]
        }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

    }
    fun entrance(){
        spinner = binding.entrance
        val entrance = arrayOf(
            "Bihar D.EL.ED",
            "Bihar CET,B.ED",
            "Bihar STET",
            "Bihar ITI(ITICAT)",
            "Bihar DCECE",
            "Bihar BCECE",
            "Navodaya Vidyalay(JNVST)",
            "Sainik School(AISSEE)",
            "Scholorship NMMSS(Class-8th)",
            "Scholorship NTSE(10th)",
            "NEET",
            "JEE Mains",
            "JEE Advance",
            "None"

        )

        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, entrance)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        binding.entrance.onItemSelectedListener = object : AdapterView.OnItemSelectedListener { override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedCountry = entrance[position]
        }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

    }
    fun currentAffairs(){
        spinner = binding.currentaffairs
        val currentaffairs = arrayOf(
            "Annual Current Affairs",
            "Half Yearly Current Affairs",
            "Monthly Current Affairs",
            "Today Current Affairs",
            "None"

        )

        val adapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, currentaffairs)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        binding.currentaffairs.onItemSelectedListener = object : AdapterView.OnItemSelectedListener { override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedCountry = currentaffairs[position]
        }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

    }

    fun openFilePicker(requestCode: Int) {
        val mimeType = "image/*"
        // Launch the file picker based on the MIME type
        when (requestCode) {
            1 -> pickImage.launch(mimeType)
            2 -> pickImage2.launch(mimeType)
            else -> throw IllegalArgumentException("Unsupported request code: $requestCode")
        }
    }

    private fun saveFileToDatabase() {
        val name = binding.name.text.toString()
        val phone = binding.phone.text.toString()
        val email = binding.email.text.toString()
        val address = binding.address.text.toString()

        val schoolExam = binding.schoolAcadmicExam.selectedItem.toString()
        val sscExam = binding.ssc.selectedItem.toString()
        val railwayExam = binding.railway.selectedItem.toString()
        val defenseExam = binding.defence.selectedItem.toString()
        val policeExam = binding.police.selectedItem.toString()
        val civilservice = binding.cicilServices.selectedItem.toString()
        val banking = binding.banking.selectedItem.toString()
        val entrance = binding.entrance.selectedItem.toString()
        val currentaffairs = binding.currentaffairs.selectedItem.toString()


        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty()) {
            // Show a required message if any text field is empty
            binding.name.error = "Require Name"
            binding.phone.error = "Require Phone-No."
            binding.email.error = "Require email I'd"
            binding.address.error = "Require Address"
            return
        }

        if (selectedImageUri == null || !isImageSelected || selectedImageUri2 == null || !isImageSelected2) {
            // Show a required message if any image is not selected
            Toast.makeText(this, "Both images are required", Toast.LENGTH_SHORT).show()
            return
        }


        if (selectedImageUri != null && isImageSelected && selectedImageUri2 != null && isImageSelected2 &&
            name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty() && address.isNotEmpty()
        ) {
            val currentDate = SimpleDateFormat("yyyy_MM_dd", Locale.getDefault()).format(Date())
            val entryKey = database.push().key

            entryKey?.let {
                database.child(entryKey).child("name").setValue(name)
                database.child(entryKey).child("phone No-").setValue(phone)
                database.child(entryKey).child("email").setValue(email)
                database.child(entryKey).child("address").setValue(address)
                database.child(entryKey).child("schoolExam").setValue(schoolExam)
                database.child(entryKey).child("sscExam").setValue(sscExam)
                database.child(entryKey).child("railwayExam").setValue(railwayExam)
                database.child(entryKey).child("defenseExam").setValue(defenseExam)
                database.child(entryKey).child("policeExam").setValue(policeExam)
                database.child(entryKey).child("civilServices").setValue(civilservice)
                database.child(entryKey).child("banking").setValue(banking)
                database.child(entryKey).child("entrance").setValue(entrance)
                database.child(entryKey).child("currentAffairs").setValue(currentaffairs)
                database.child(entryKey).child("CurrentDate").setValue(currentDate)

                uploadImageToStorage(selectedImageUri, 1,  entryKey)

                uploadImageToStorage(selectedImageUri2, 2, entryKey)

                binding.name.text?.clear()
                binding.phone.text?.clear()
                binding.email.text?.clear()
                binding.address.text?.clear()

                // Set default image to the image views
                binding.imagechoose.setImageResource(R.drawable.ic_menu_gallery)
                binding.sspay.setImageResource(R.drawable.ic_menu_gallery)

                isImageSelected = false
                isImageSelected2 = false


                Toast.makeText(this, "data uploded", Toast.LENGTH_SHORT).show()
            }
        } else {
        }
    }
    private fun uploadImageToStorage(uri: Uri?, requestCode: Int, entryKey: String) {
        uri?.let {
            val storageReference =
                FirebaseStorage.getInstance().reference.child("images/${System.currentTimeMillis()}_${requestCode}.jpg")

            storageReference.putFile(uri)
                .addOnSuccessListener { taskSnapshot ->
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

    private fun paymentMode() {
        val easyUpiPayment = EasyUpiPayment.Builder()
            .setPayeeVpa("7739717389@upi")
            .setPayeeName("Shruti")
            .setDescription(binding.dis.text.toString())
            .setAmount(binding.amount.text.toString())
            .setTransactionId("12345567890")
            .setTransactionRefId("12345567890")
            .build()

        // Set the payment status listener
        easyUpiPayment.setPaymentStatusListener(this)

        // Call startPayment after setting the payment status listener
        easyUpiPayment.startPayment()
    }

    override fun onTransactionCompleted(transactionDetails: TransactionDetails?) {
        // Handle transaction completion
//        if (transactionDetails != null) {
//            when (transactionDetails.transactionStatus) {
//                TransactionStatus.SUCCESS -> {
//                    // Payment successful
//                    Toast.makeText(this, "Transaction Successful", Toast.LENGTH_SHORT).show()
//                }
//                TransactionStatus.FAILURE -> {
//                    // Payment failed
//                    Toast.makeText(this, "Transaction Failed", Toast.LENGTH_SHORT).show()
//                }
//                TransactionStatus.SUBMITTED -> {
//                    // Payment submitted, waiting for confirmation
//                    Toast.makeText(this, "Transaction Submitted", Toast.LENGTH_SHORT).show()
//                }
//            }
//        } else {
//            // Handle null transactionDetails
//            Toast.makeText(this, "Transaction Details Null", Toast.LENGTH_SHORT).show()
//        }
    }

    override fun onTransactionSuccess() {
        TODO("Not yet implemented")
    }

    override fun onTransactionSubmitted() {
        TODO("Not yet implemented")
    }

    override fun onTransactionFailed() {
        TODO("Not yet implemented")
    }

    override fun onTransactionCancelled() {
        TODO("Not yet implemented")
    }

    override fun onAppNotFound() {
        TODO("Not yet implemented")
    }



// Implement other methods as needed...

}