package com.shrutipandit.gotopacademy.activity

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.android.material.tabs.TabLayout
import com.shrutipandit.gotopacademy.R
import com.shrutipandit.gotopacademy.databinding.ActivityFormBinding
import com.shrutipandit.gotopacademy.fragment.LibrabryFragment
import com.shrutipandit.gotopacademy.fragment.SchoolFragment
import com.shrutipandit.gotopacademy.fragment.TestFragment
import com.shrutipandit.gotopacademy.utils.Validate
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Form : AppCompatActivity() {
    private lateinit var binding: ActivityFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup TabLayout with ViewPager
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val adapter = ViewPagerAdapter(supportFragmentManager)

        // Add fragments for each tab
        adapter.addFragment(TestFragment(), "Test Series")
        adapter.addFragment(LibrabryFragment(), "Library")
        adapter.addFragment(SchoolFragment(), "School")
        // Add more fragments as needed

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }


    internal class ViewPagerAdapter(manager: FragmentManager) :
        FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val fragments: MutableList<Fragment> = ArrayList()
        private val titles: MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragments.add(fragment)
            titles.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }
    }
}
