package com.ahmadfzlbs.lp3icheckin.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ahmadfzlbs.lp3icheckin.fragment.OnboardingFragment1
import com.ahmadfzlbs.lp3icheckin.fragment.OnboardingFragment2
import com.ahmadfzlbs.lp3icheckin.fragment.OnboardingFragment3

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