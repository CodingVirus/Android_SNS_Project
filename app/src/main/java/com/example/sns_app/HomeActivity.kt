package com.example.sns_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout

class HomeActivity : AppCompatActivity() {

    private val homeButton: ImageButton by lazy {findViewById(R.id.btn_home)}
    private val myPageButton: ImageButton by lazy {findViewById(R.id.btn_my_page)}
    private val addButton: ImageButton by lazy {findViewById(R.id.btn_add)}
    private val postLayout: LinearLayout by lazy { findViewById(R.id.main_post) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        myPageButton.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }

        addButton.setOnClickListener {
            val intent = Intent(this, AddPostActivity::class.java)
            startActivity(intent)

        }

    }


}