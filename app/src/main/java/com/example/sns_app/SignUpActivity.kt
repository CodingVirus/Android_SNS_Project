package com.example.sns_app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class SignUpActivity : AppCompatActivity() {

    lateinit var db: DBHelper
    var users = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        db = DBHelper(this)

        val inputEmail = findViewById<EditText>(R.id.et_inputEmail)
        val inputPw = findViewById<EditText>(R.id.et_inputPw)
        val inputName = findViewById<EditText>(R.id.et_inputName)
        val regiButton = findViewById<Button>(R.id.btn_register)
        val backButton = findViewById<ImageView>(R.id.btn_back)

        regiButton.setOnClickListener {

            val email: String = inputEmail.text.toString()
            val pw: String = inputPw.text.toString()
            val name: String = inputName.text.toString()

            if (inputEmail.text.isEmpty() || inputPw.text.isEmpty() || inputName.text.isEmpty()) {
                Toast.makeText(this, R.string.not_input_text, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, SignInActivity::class.java)
            intent.putExtra("email", email)
            intent.putExtra("pw", pw)

            Log.d("SignUp email : ", email)
            Log.d("pw : ", pw)
            Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT).show()
//            startActivity(intent)

            createUser().let {
                if (it != null) {
                    db.addUser(it)
                }
            }

            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onDestroy() {
        db.close()
        super.onDestroy()
    }

     private fun createUser(): User?{
        val email = findViewById<EditText>(R.id.et_inputEmail).text.toString()
        val pw = findViewById<EditText>(R.id.et_inputPw).text.toString()
        val name = findViewById<EditText>(R.id.et_inputName).text.toString()
        if(email == "" || pw == "" || name =="") // 입력 정보가 하나라도 비어있으면
            return null // Null 반환

        return User(email,pw,name)
    }

}

