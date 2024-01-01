package com.example.sns_app

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
  

    
    init {
        instance = this
    }

    companion object {
        private var instance: HomeActivity? = null

        fun getInstance(): HomeActivity? 		{
            return instance
        }
    }
    
    private val homeButton: ImageView by lazy {findViewById(R.id.btn_home)}
    private val myPageButton: ImageView by lazy {findViewById(R.id.btn_my_page)}
    private val addButton: ImageView by lazy {findViewById(R.id.btn_add)}
//    private val postLayout: LinearLayout by lazy { findViewById(R.id.main_post) }
    private lateinit var firestore: FirebaseFirestore
    private var uid: String? = null
    private lateinit var auth: FirebaseAuth
  
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<UserTest>
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerView = findViewById(R.id.recycler_post_view)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)

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


    fun updatePost() {

        dataList = arrayListOf<UserTest>()

        database = FirebaseDatabase.getInstance().getReference("Users")
        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {
                    val imageUri = i.child("1").child("imageUri").value
                    val userName = i.child("1").child("userName").value
                    val comment = i.child("1").child("comment").value

                    val dataClass = UserTest()
                    dataClass.userName = userName.toString()
                    dataClass.imageUri = imageUri.toString()
                    dataClass.comment = comment.toString()
                    dataList.add(dataClass)

                }

                recyclerView.adapter = MyRecyclerAdapter(dataList)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

//        database.child("Minyong").child("1").get().addOnSuccessListener {
//
//            if (it.exists()) {
//
//                val imageUri = it.child("imageUri").value
//                val userName = it.child("userName").value
//                val comment = it.child("comment").value
//
//                val dataClass = UserTest()
//                dataClass.userName = userName.toString()
//                dataClass.imageUri = imageUri.toString()
//                dataClass.comment = comment.toString()
//                dataList.add(dataClass)
//
//                recyclerView.adapter = MyRecyclerAdapter(dataList)
//
//            } else {
//                Toast.makeText(this, "User Doesn't Exist", Toast.LENGTH_SHORT).show()
//            }
//        }.addOnFailureListener {
//            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
//        }

    }
}