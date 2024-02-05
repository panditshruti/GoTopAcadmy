package com.example.shrutiPandit

import com.example.shrutiPandit.adapter.NoticeAdapterN
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shrutiPandit.db.NoticeItem

import com.example.shrutiPandit.databinding.ActivityNoticeBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Notice : AppCompatActivity() {
    private lateinit var binding: ActivityNoticeBinding
    private lateinit var db: DatabaseReference
    private lateinit var arrayList: ArrayList<NoticeItem>
    private lateinit var noticeAdapter: NoticeAdapterN


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseDatabase.getInstance().reference.child("Notice")
        arrayList = arrayListOf()




        binding.recyclerview.adapter = noticeAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        fetchNotice()

    }

    private fun fetchNotice() {
        db.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (data in snapshot.children) {
                        val title = data.child("title").value as? String
                        val link = data.child("link").value as? String
                        val img = data.child("imageUrl").value as? String
                        val pdf = data.child("pdfurl").value as? String
                        val date = data.child("date").value as? String

                        Log.d("Notice", "Title: $title, Link: $link, Image: $img, PDF: $pdf")


                            arrayList.add(NoticeItem(img, pdf, title, link,date!!))

                    }

                    noticeAdapter.notifyDataSetChanged()
                }
            }


            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}


