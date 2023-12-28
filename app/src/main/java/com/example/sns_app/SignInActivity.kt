package com.example.sns_app

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class SignInActivity : AppCompatActivity() {

    lateinit var db: DBHelper
    var users = ArrayList<User>()
    lateinit var resultLauncher: ActivityResultLauncher<Intent>
    lateinit var inputEmail: EditText
    lateinit var inputPw: EditText
    lateinit var inputName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        db = DBHelper(this)

        val inputEmail = findViewById<EditText>(R.id.et_inputEmail)
        val inputPw = findViewById<EditText>(R.id.et_inputPw)
        val inputName = findViewById<TextView>(R.id.tv_inputName)
        val loginButton = findViewById<Button>(R.id.btn_login)
        val regiButton = findViewById<TextView>(R.id.tv_createAccount)
        val findEmail = findViewById<TextView>(R.id.tv_forgotPassword)

        loginButton.setOnClickListener {

            if (inputEmail.text.isEmpty()) {
                Toast.makeText(this, R.string.email_check, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (inputPw.text.isEmpty()) {
                Toast.makeText(this, R.string.password_check, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val email : String = inputEmail.text.toString()
            val pw : String = inputPw.text.toString()


            val intent = Intent(this, MainActivity::class.java)
//            intent.putExtra("email", email)
//            intent.putExtra("password", pw)
//
//            Log.d("Login email : ", email)
//            Log.d("pw : ", pw)
//            Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show()

            createUser().let {
                if (it != null) {
                    if (db.login(it)) {
                        Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show()
//                        val intent = Intent(this, MainActivity::class.java)
                        val name : String = inputName.text.toString()

                        intent.putExtra("email", email)
                        intent.putExtra("password", pw)
                        intent.putExtra("name", name)
                        setResult(Activity.RESULT_OK, intent)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, R.string.login_failure, Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "정보를 모두 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }

        regiButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            resultLauncher.launch(intent)
        }

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                    result ->
                if (result.resultCode == RESULT_OK) {
                    val email = result.data?.getStringExtra("id") ?: ""
                    val pw = result.data?.getStringExtra("pw") ?: ""
                    val name = result.data?.getStringExtra("name") ?: ""
                    inputEmail.setText(email)
                    inputPw.setText(pw)
                    inputName.setText(name)
                }
            }
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