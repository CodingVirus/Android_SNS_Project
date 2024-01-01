package com.example.sns_app

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import org.checkerframework.checker.nullness.qual.NonNull


class HomeActivity : AppCompatActivity() {

    private val homeButton: ImageButton by lazy {findViewById(R.id.btn_home)}
    private val myPageButton: ImageButton by lazy {findViewById(R.id.btn_my_page)}
    private val addButton: ImageButton by lazy {findViewById(R.id.btn_add)}
    private val postLayout: LinearLayout by lazy { findViewById(R.id.main_post) }
    private lateinit var firestore: FirebaseFirestore
    private var uid: String? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        uid = FirebaseAuth.getInstance().currentUser?.uid
        firestore = FirebaseFirestore.getInstance()
        auth = Firebase.auth

        val intent = intent
        val tv_Email = findViewById<TextView>(R.id.tv_email)
        val tv_Name = findViewById<TextView>(R.id.tv_name)
        val tv_uid = findViewById<TextView>(R.id.tv_uid)

        val email = intent.getStringExtra("email").toString()
        val name = intent.getStringExtra("name").toString()
        val password = intent.getStringExtra("password").toString()

        var userDTO = UserDTO()
        userDTO.uid = auth?.currentUser?.uid
        userDTO.email = email
        userDTO.name = name

        firestore.collection("users")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        Log.d(TAG, document.id + " => " + document.data)
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

                            tv_Email.text = "Email: $emailFromFirestore"
                            tv_Name.text = "name: $nameFromFirestore"
                            tv_uid.text = "uid: $uidFromFirestore"
                        }
                    }
                } else {
                    Log.w(TAG, "Error => ", task.exception)
                }
            }

        myPageButton.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }

        addButton.setOnClickListener {
            val intent = Intent(this, AddPostActivity::class.java)
            startActivity(intent)

        }

//        tv_loginResult.text = "안녕하세요 ${name}님"
    }


}