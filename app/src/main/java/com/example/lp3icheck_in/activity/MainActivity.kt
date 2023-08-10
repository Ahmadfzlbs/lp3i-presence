package com.example.lp3icheck_in.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lp3icheck_in.R
import com.example.lp3icheck_in.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.viewPager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
    }
}
