package com.example.sns_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class AddPostActivity : AppCompatActivity() {

    private val backButton: ImageButton by lazy { findViewById(R.id.btn_back) }
    private val addPostButton: ImageButton by lazy { findViewById(R.id.btn_add_post) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        backButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }


    }
}