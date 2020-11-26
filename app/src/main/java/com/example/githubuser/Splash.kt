package com.example.githubuser

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import java.util.logging.Handler

class Splash : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        android.os.Handler().postDelayed({
            startActivity(Intent(this@Splash, MainActivity:: class.java))
            finish()
        },600)
    }
}