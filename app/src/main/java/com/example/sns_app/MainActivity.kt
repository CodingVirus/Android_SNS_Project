package com.example.sns_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
<<<<<<< HEAD
=======

        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
>>>>>>> 604ebc9c2d404379c11a65726cf0175858f86eea
    }
}

