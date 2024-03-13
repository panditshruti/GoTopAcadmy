package com.shrutipandit.gotopacademy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shrutipandit.gotopacademy.R
import com.shrutipandit.gotopacademy.databinding.FragmentNotesBinding

class NotesFragment : Fragment(R.layout.fragment_notes) {
    private lateinit var binding:FragmentNotesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotesBinding.bind(view)


    }
}
