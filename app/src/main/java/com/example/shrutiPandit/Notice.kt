package com.example.shrutiPandit

import NoticeAdapter
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
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
    private lateinit var noticeAdapter: NoticeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseDatabase.getInstance().reference.child("Notice")
        arrayList = arrayListOf()
        fetchNotes()

        noticeAdapter = NoticeAdapter(arrayList)
        binding.recyclerview.adapter = noticeAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)

    }
    private fun fetchNotes() {
        db.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (data in snapshot.children) {
                        val title = data.child("title").value as? String
                        val link = data.child("link").value as? String
                        val image = data.child("uri").value as? String
                        if (title != null && link != null && image != null) {
                            arrayList.add(NoticeItem(image, title, link))

                        }
                    }
                    noticeAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error if needed
            }
        })
    }
}
