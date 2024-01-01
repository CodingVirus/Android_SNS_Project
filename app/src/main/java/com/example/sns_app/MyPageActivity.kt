package com.example.sns_app

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class MyPageActivity : AppCompatActivity() {
  
    private val tabLayout: TabLayout by lazy { findViewById(R.id.tab_layout) }
    private val postGridLayout: GridLayout by lazy { findViewById(R.id.grid_my_post) }
    private val editProfileButton: Button by lazy { findViewById(R.id.btn_edit_profile) }


    private lateinit var firestore: FirebaseFirestore
    private var uid: String? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        uid = FirebaseAuth.getInstance().currentUser?.uid
        firestore = FirebaseFirestore.getInstance()
        auth = Firebase.auth

        val intent = intent
        val tvName = findViewById<TextView>(R.id.tv_name)
        var email: String = ""
        var name: String = ""

        var userDTO = UserDTO()
        userDTO.uid = auth?.currentUser?.uid
//        userDTO.name = name

        firestore.collection("users")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        Log.d(ContentValues.TAG, document.id + " => " + document.data)
                        val dataMap = document.data
                        if (dataMap["uid"] == auth?.currentUser?.uid) {
                            var emailFromFirestore = dataMap["email"].toString()
                            var nameFromFirestore = dataMap["name"].toString()
                            var uidFromFirestore = dataMap["uid"].toString()

                            if (emailFromFirestore.isEmpty()) {
                                emailFromFirestore = "No email found"
                            }

                            if (nameFromFirestore.isEmpty()) {
                                nameFromFirestore = "No name found"
                            }

                            if (uidFromFirestore.isEmpty()) {
                                uidFromFirestore = "No uid found"
                            }

                            tvName.text = "name: $nameFromFirestore"
                            email = emailFromFirestore
                            name = nameFromFirestore

                        }
                    }
                } else {
                    Log.w(ContentValues.TAG, "Error => ", task.exception)
                }
            }

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
            val intent = Intent(this, EditProfileActivity::class.java)

            intent.putExtra("email", email)
            intent.putExtra("name",name)
            startActivity(intent)
        }

    }
}