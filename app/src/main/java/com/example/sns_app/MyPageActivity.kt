package com.example.sns_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import com.google.android.material.tabs.TabLayout
import android.provider.MediaStore
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.io.FileNotFoundException
import java.io.IOException


class MyPageActivity : AppCompatActivity() {
  
    private val tabLayout: TabLayout by lazy { findViewById(R.id.tab_layout) }
    private val postGridLayout: GridLayout by lazy { findViewById(R.id.grid_my_post) }
    private val editProfileButton: Button by lazy { findViewById(R.id.btn_edit_profile) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)


        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    0 -> {
                        postGridLayout.visibility = View.VISIBLE
                    }

                    1 -> {
                        postGridLayout.visibility = View.INVISIBLE
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        // 프로필 편집 버튼을 눌렀을 경우
        editProfileButton.setOnClickListener {

        }

    }
}