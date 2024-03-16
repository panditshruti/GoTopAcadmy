package com.shrutipandit.gotopacademy.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.shrutipandit.gotopacademy.R
import com.shrutipandit.gotopacademy.adapter.NoticeAdapterN
import com.shrutipandit.gotopacademy.databinding.FragmentDppBinding
import com.shrutipandit.gotopacademy.databinding.FragmentSearchBinding
import com.shrutipandit.gotopacademy.db.NoticeItem


class searchFragment : Fragment(R.layout.fragment_search) {
        private lateinit var binding: FragmentSearchBinding

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            binding = FragmentSearchBinding.bind(view)

            binding.linkTranslator.setOnClickListener {
                val link = "https://g.co/kgs/Huy4Xmy"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                startActivity(intent)
            }

          binding.vacancySearch.setOnClickListener {
                val link = "https://www.sarkariresult.com/"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                startActivity(intent)
            }

        }
    }
