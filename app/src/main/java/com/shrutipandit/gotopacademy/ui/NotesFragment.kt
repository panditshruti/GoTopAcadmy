package com.shrutipandit.gotopacademy.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.shrutipandit.gotopacademy.R
import com.shrutipandit.gotopacademy.databinding.FragmentNotesBinding

class NotesFragment : Fragment(R.layout.fragment_notes) {
    private lateinit var binding:FragmentNotesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotesBinding.bind(view)

        binding.rspaynote.setOnClickListener {
            val link = "https://wa.me/+7739717389"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
            startActivity(intent)
        }
   binding.paynote.setOnClickListener {
            val link = "7739717389"
          copyToClipboard(link)
        }


    }

    private fun copyToClipboard(text: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("phoneNumber", text)
        clipboard.setPrimaryClip(clip)

        Toast.makeText(requireContext(), "Copied: $text", Toast.LENGTH_SHORT).show()
    }
}
