package com.example.sns_app

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.marginBottom
import androidx.core.view.setMargins
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sns_app.databinding.ActivityHomeBinding
import com.example.sns_app.databinding.LayoutRecyclerItemBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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

    lateinit var db: DBHelper
    var users = ArrayList<User>()

    private val homeButton: ImageView by lazy {findViewById(R.id.btn_home)}
    private val myPageButton: ImageView by lazy {findViewById(R.id.btn_my_page)}
    private val addButton: ImageView by lazy {findViewById(R.id.btn_add)}
    //private val postLayout: LinearLayout by lazy { findViewById(R.id.main_post) }

    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<UserTest>

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerView = findViewById(R.id.recycler_post_view)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)



        db = DBHelper(this)

        val intent = intent
        val tv_Email = findViewById<TextView>(R.id.tv_email)
        val tv_Name = findViewById<TextView>(R.id.tv_name)
        val tv_password = findViewById<TextView>(R.id.tv_password)

        val email = intent.getStringExtra("email").toString()
        val name = intent.getStringExtra("name").toString()
        val password = intent.getStringExtra("password").toString()
//        val users = intent.getSerializableExtra("users") as ArrayList<User>
//
//        createUser().let {
//            if (it != null) {
//                if (db.select(it)) {
//                    tv_Email.setText(email)
//                    tv_Name.setText(name)
//                    tv_password.setText(password)
//                }
//            }
//        }

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

    private fun createUser(): User?{
        val inputEmail = findViewById<EditText>(R.id.et_inputEmail)
        val inputPw = findViewById<EditText>(R.id.et_inputPw)
        val inputName = findViewById<TextView>(R.id.tv_inputName)
        val email = inputEmail.text.toString()
        val pw = inputPw.text.toString()
        var name = inputName.text.toString()
        if(email == "" || pw == "") // 입력 정보가 하나라도 비어있으면
            return null // Null 반환
        if (name == null) {
            name = "a"
        }
        return User(email,pw,name)




    }


}