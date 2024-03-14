package com.shrutipandit.gotopacademy.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.shrutipandit.gotopacademy.R
import com.shrutipandit.gotopacademy.databinding.FragmentHelpCenterBinding

class HelpCenterFragment : Fragment(R.layout.fragment_help_center) {
    private lateinit var binding: FragmentHelpCenterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHelpCenterBinding.bind(view)

        binding.gmail.setOnClickListener {
            val email = "gotopacademy004@gmail.com"
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
            }
            startActivity(intent)
        }

        binding.call.setOnClickListener {
            val phoneNumber = "9708379004"
            dialPhoneNumber(phoneNumber)
            copyToClipboard(phoneNumber)
        }
    }

    private fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        startActivity(intent)
    }

    private fun copyToClipboard(text: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("phoneNumber", text)
        clipboard.setPrimaryClip(clip)
        // Show a toast message to inform the user that the text has been copied
        // You can customize this message according to your app's requirements
        Toast.makeText(requireContext(), "Copied: $text", Toast.LENGTH_SHORT).show()
    }
}
