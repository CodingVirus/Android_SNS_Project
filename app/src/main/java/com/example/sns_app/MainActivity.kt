package com.example.sns_app

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.util.Locale

class MainActivity : AppCompatActivity() {

    lateinit var db: DBHelper
    var users = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = DBHelper(this)

        val intent = intent
        val tv_loginResult = findViewById<TextView>(R.id.tv_loginResult)
        val name = intent.getStringExtra("name")
        val users = intent.getSerializableExtra("users") as ArrayList<User>

        val userListAdapter = UserListAdapter()
        userListAdapter.submitList(users)
        binding.recyclerView.adapter = userListAdapter

        tv_loginResult.text = "안녕하세요 ${name}님"

    }
}