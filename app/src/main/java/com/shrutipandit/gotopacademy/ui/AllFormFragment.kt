package com.shrutipandit.gotopacademy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.shrutipandit.gotopacademy.R
import com.shrutipandit.gotopacademy.databinding.FragmentAllFormBinding


class AllFormFragment : Fragment(R.layout.fragment_all_form) {
    private lateinit var binding: FragmentAllFormBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllFormBinding.bind(view)

        // Setup TabLayout with ViewPager
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        val adapter = ViewPagerAdapter(childFragmentManager) // Use childFragmentManager for fragments within a fragment

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
