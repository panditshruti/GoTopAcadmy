package com.shrutipandit.gotopacademy.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.shrutipandit.gotopacademy.R
import com.shrutipandit.gotopacademy.adapter.ImageSliderAdapter
import com.shrutipandit.gotopacademy.databinding.FragmentHomeBinding
import java.util.Timer
import java.util.TimerTask

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewPager: ViewPager
    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private val images = intArrayOf(
        R.drawable.img2,
        R.drawable.img1,
        R.drawable.logogotop,
        R.drawable.pred,
        R.drawable.pgreen
    )
    // Timer and handler for auto-scrolling
    private var currentPage = 0
    private val DELAY_MS: Long = 6000 // Delay in milliseconds before the next page is shown.
    private val PERIOD_MS: Long = 6000 // Interval time to repeat.

    private val handler = Handler(Looper.getMainLooper())
    private val update = Runnable {
        if (currentPage == images.size) {
            currentPage = 0
        }
        viewPager.setCurrentItem(currentPage++, true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        // Corrected binding for viewPager
        viewPager = binding.viewPager
        imageSliderAdapter = ImageSliderAdapter(requireContext(), images)
        viewPager.adapter = imageSliderAdapter

        // Set up auto-scrolling
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, DELAY_MS, PERIOD_MS)

        binding.form.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToAllFormFragment()
            findNavController().navigate(action)
        }

        binding.result.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToResultFragment()
            findNavController().navigate(action)
        }

        binding.notice.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToNoticeFragment()
            findNavController().navigate(action)
        }

        binding.news.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToNewsFragment2()
            findNavController().navigate(action)
        }
        binding.book.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToBookFragment()
            findNavController().navigate(action)
        }
        binding.dpp.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDppFragment()
            findNavController().navigate(action)
        }
   binding.stanza.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToStanzaFragment()
            findNavController().navigate(action)
        }
        binding.search.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            findNavController().navigate(action)
        }
  binding.notes.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToNotesFragment()
            findNavController().navigate(action)
        }
        binding.faq.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToFaqFragment()
            findNavController().navigate(action)
        }


    }
}
