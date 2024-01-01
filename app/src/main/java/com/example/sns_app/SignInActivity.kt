package com.example.sns_app

import android.app.Activity
import android.content.ContentValues.TAG
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {

    lateinit var resultLauncher: ActivityResultLauncher<Intent>
    lateinit var inputEmail: EditText
    lateinit var inputPw: EditText
    lateinit var inputName: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val inputEmail = findViewById<EditText>(R.id.et_inputEmail)
        val inputPw = findViewById<EditText>(R.id.et_inputPw)
        val inputName = findViewById<TextView>(R.id.tv_inputName)
        val loginButton = findViewById<Button>(R.id.btn_login)
        val regiButton = findViewById<TextView>(R.id.tv_createAccount)
        val forgotPw = findViewById<TextView>(R.id.tv_forgotPassword)

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
            val password : String = inputPw.text.toString()

            val intent = Intent(this, HomeActivity::class.java)

            signIn(email, password)
//            intent.putExtra("email", email)
//            intent.putExtra("password", password)
//            setResult(Activity.RESULT_OK, intent)
//            startActivity(intent)

        }

        regiButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            resultLauncher.launch(intent)
        }

        forgotPw.setOnClickListener {
            val intent = Intent(this, FindActivity::class.java)
            startActivity(intent)
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

    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    val intent = Intent(this, HomeActivity::class.java)
                    val uid = auth?.currentUser?.uid
                    updateUI(user)
                    intent.putExtra("email", email)
                    intent.putExtra("password", password)
                    intent.putExtra("uid", uid)
                    setResult(Activity.RESULT_OK, intent)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
        // [END sign_in_with_email]
    }

    private fun updateUI(user: FirebaseUser?) {
    }
}