package com.ahmadfzlbs.lp3icheckin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import com.ahmadfzlbs.lp3icheckin.R
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if(auth.currentUser != null){
            Handler(Looper.getMainLooper()).postDelayed({
                val i = Intent(this, MenuActivity::class.java)
                startActivity(i)
                finish()
            }, 1800)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                val i = Intent(this, MainActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(i)
            }, 1800)
        }
    }
}