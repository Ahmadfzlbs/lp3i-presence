package com.ahmadfzlbs.lp3icheckin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ahmadfzlbs.lp3icheckin.R
import com.ahmadfzlbs.lp3icheckin.fragment.HistoryFragment
import com.ahmadfzlbs.lp3icheckin.fragment.HomeFragment
import com.ahmadfzlbs.lp3icheckin.fragment.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MenuActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        bottomNavigationView = findViewById(R.id.bottomNav)

        replaceFrafment(HomeFragment())
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.home -> {
                    replaceFrafment(HomeFragment())
                    true
                }
                R.id.riwayat -> {
                    replaceFrafment(HistoryFragment())
                    true
                }
                R.id.profile -> {
                    replaceFrafment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }
    private fun replaceFrafment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
    }
}