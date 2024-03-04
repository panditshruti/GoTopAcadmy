package com.shrutipandit.gotopacademy.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.shrutipandit.gotopacademy.R
import com.shrutipandit.gotopacademy.adapter.NoticeAdapterN
import com.shrutipandit.gotopacademy.databinding.FragmentResultBinding
import com.shrutipandit.gotopacademy.db.NoticeItem

class ResultFragment : Fragment(R.layout.fragment_result) {
    private lateinit var binding: FragmentResultBinding


    private lateinit var db: DatabaseReference
    private lateinit var arrayList: ArrayList<NoticeItem>
    private lateinit var noticeAdapter: NoticeAdapterN


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentResultBinding.bind(view)

        db = FirebaseDatabase.getInstance().reference.child("Result")
        arrayList = arrayListOf()



        noticeAdapter = NoticeAdapterN(arrayList, requireContext())
        binding.recyclerview.adapter = noticeAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())



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
                        val pdf = data.child("pdfUrl").value as? String
                        val date = data.child("date").value as? String

                        Log.d("Notice", "Title: $title, Link: $link, Image: $img, PDF: $pdf")


                        arrayList.add(NoticeItem(img, pdf, title, link, date!!))

                    }

                    noticeAdapter.notifyDataSetChanged()
                }
            }


            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

}
