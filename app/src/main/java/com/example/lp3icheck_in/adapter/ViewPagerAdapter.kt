package com.example.lp3icheck_in.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.lp3icheck_in.fragment.OnboardingFragment1
import com.example.lp3icheck_in.fragment.OnboardingFragment2
import com.example.lp3icheck_in.fragment.OnboardingFragment3

class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

   private val fragment = listOf(
       OnboardingFragment1(),
       OnboardingFragment2(),
       OnboardingFragment3()
   )

    override fun getCount(): Int {
        return fragment.size
    }

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }
}