package com.example.sns_app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity

class SearchPageActivity : AppCompatActivity() {


    private val homeButton: ImageButton by lazy { findViewById(R.id.iv_home) }
    private val myPageButton: ImageButton by lazy { findViewById(R.id.iv_my_page) }
    private val addButton: ImageButton by lazy { findViewById(R.id.iv_add) }
//    private val postLayout: LinearLayout by lazy { findViewById(R.id.main_post) }


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_page)

        val searchView = findViewById<SearchView>(R.id.search_view);

//      val iv_one = findViewById<ImageView>(R.id.page_one)
        val iv_two = findViewById<ImageView>(R.id.page_two)
        val iv_three = findViewById<ImageView>(R.id.page_three)
        val iv_four = findViewById<ImageView>(R.id.page_four)


        val iv_one = findViewById<ImageView>(R.id.page_one)
        iv_one.setOnClickListener {
            val intent = Intent(this, ActivityPost::class.java)
            startActivity(intent)
        }
    }
}





