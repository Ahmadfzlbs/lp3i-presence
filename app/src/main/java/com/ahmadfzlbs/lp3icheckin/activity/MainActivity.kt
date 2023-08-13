package com.ahmadfzlbs.lp3icheckin.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ahmadfzlbs.lp3icheckin.R
import com.ahmadfzlbs.lp3icheckin.adapter.ViewPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import kotlinx.android.synthetic.main.activity_main.viewPager

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
    }
}
