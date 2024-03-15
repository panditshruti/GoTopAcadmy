package com.shrutipandit.gotopacademy.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.shrutipandit.gotopacademy.R
import com.shrutipandit.gotopacademy.databinding.FragmentAboutUsBinding

class AboutUsFragment : Fragment(R.layout.fragment_about_us) {
    private lateinit var binding: FragmentAboutUsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAboutUsBinding.bind(view)

        binding.devno.setOnLongClickListener {
            val num = "7739717389"
            copyToClipboard(num)
            true // Return true to indicate that the long click event is consumed
        }

        binding.pracharmeapp.setOnClickListener {
            val link = "https://play.google.com/store/apps/details?id=com.shrutipandit.computercource"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
            copyTolinkplay(link)

        }


    }

    private fun copyToClipboard(text: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("phoneNumber", text)
        clipboard.setPrimaryClip(clip)

        Toast.makeText(requireContext(), "Copied: $text", Toast.LENGTH_SHORT).show()
    }

    private fun copyTolinkplay(text: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Link", text)
        clipboard.setPrimaryClip(clip)

        Toast.makeText(requireContext(), "Link copied to clipboard", Toast.LENGTH_SHORT).show()
    }
}
