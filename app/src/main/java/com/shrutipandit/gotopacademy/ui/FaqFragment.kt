package com.shrutipandit.gotopacademy.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shrutipandit.gotopacademy.R
import com.shrutipandit.gotopacademy.databinding.FragmentFaqBinding


class FaqFragment : Fragment(R.layout.fragment_faq) {
    private lateinit var binding: FragmentFaqBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFaqBinding.bind(view)


        binding.linkwatsapFaq.setOnClickListener {
            val link = "https://chat.whatsapp.com/ITlj9NwtP4o9S2FcNNsNlx"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        }




    }
}
