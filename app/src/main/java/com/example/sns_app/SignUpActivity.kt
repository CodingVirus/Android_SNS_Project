package com.example.sns_app

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val inputEmail = findViewById<EditText>(R.id.et_inputEmail)
        val inputPw = findViewById<EditText>(R.id.et_inputPw)
        val inputPwValid = findViewById<EditText>(R.id.et_inputPwValid)
        val inputName = findViewById<EditText>(R.id.et_inputName)
        val regiButton = findViewById<Button>(R.id.btn_register)
        val backButton = findViewById<ImageView>(R.id.btn_back)
        val alreadyAccount = findViewById<TextView>(R.id.tv_alreadyAccount)

        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()

        regiButton.setOnClickListener {

            val name: String = inputName.text.toString()
            val email: String = inputEmail.text.toString()
            val password: String = inputPw.text.toString()
            val password_ok: String = inputPwValid.text.toString()

            if (inputEmail.text.isEmpty() || inputPw.text.isEmpty() || inputPwValid.text.isEmpty() || inputName.text.isEmpty()) {
                Toast.makeText(this, R.string.not_input_text, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, SignInActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("email", email)
            intent.putExtra("password", password)
            intent.putExtra("password_ok", password_ok)

            Log.d("SignUp email : ", email)
            Log.d("password : ", password)
            Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT).show()
//            startActivity(intent)

            signUp(email, password)


//            setResult(Activity.RESULT_OK, intent)
//            finish()
        }

        backButton.setOnClickListener {
            finish()
        }

        alreadyAccount.setOnClickListener {
            finish()
        }
    }
    fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    setResult(Activity.RESULT_OK, intent)
                    updateUI(user)
                    val name = findViewById<EditText>(R.id.et_inputName).text.toString()
                    var userDTO = UserDTO()
                    userDTO.uid = auth?.currentUser?.uid
                    userDTO.email = email
                    userDTO.name = name
                    userDTO.timestamp = System.currentTimeMillis()

                    firestore?.collection("users")?.document()?.set(userDTO)
                    Toast.makeText(this,"저장완료",Toast.LENGTH_SHORT).show()
                    finish()
                } else if (task.exception?.message.isNullOrEmpty()) {
                    Toast.makeText(this, "", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, R.string.already, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
    }

}

