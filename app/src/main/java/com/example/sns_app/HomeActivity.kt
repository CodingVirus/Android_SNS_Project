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
import androidx.core.view.marginBottom
import androidx.core.view.setMargins
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance

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
    private val postLayout: LinearLayout by lazy { findViewById(R.id.main_post) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)



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
    fun createPost(img: Uri?, com: String)  {
        val imagePost = ImageView(this)
        val comment = TextView(this)

        val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val lp2 = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp2.setMargins(0, 0, 0, 80)

        imagePost.layoutParams = lp
        imagePost.setImageURI(img)


        comment.layoutParams = lp2
        comment.textSize = 40f
        comment.text = com



        postLayout.addView(comment, 0)
        postLayout.addView(imagePost, 0)
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