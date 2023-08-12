package com.ahmadfzlbs.lp3icheckin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahmadfzlbs.lp3icheckin.R
import com.ahmadfzlbs.lp3icheckin.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.viewPager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
    }
}
