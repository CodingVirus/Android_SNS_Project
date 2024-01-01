package com.example.sns_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class ActivityPost : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        val imageView = findViewById<ImageView>(R.id.move_iv);

//        //액션바 이름 설정
//        supportActionBar?.title = "activity_post"
//
//        //뒤로가기 버튼 설정
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
}