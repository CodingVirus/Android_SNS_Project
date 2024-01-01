package com.example.sns_app

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class FindActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find)

        val userEmail = findViewById<EditText>(R.id.et_userEmail)
        val findBtn = findViewById<Button>(R.id.btn_find)
        val backButton = findViewById<ImageView>(R.id.btn_back)

        // 이메일 보내기
        findBtn.setOnClickListener {
            if (it === findBtn) {
                //비밀번호 재설정 이메일 보내기
                val emailAddress = userEmail.text.toString().trim()
                Firebase.auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(OnCompleteListener<Void?> { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this@FindActivity, R.string.send_email, Toast.LENGTH_LONG).show()
                            finish()
                        } else {
                            Toast.makeText(this@FindActivity, R.string.fail_send_email, Toast.LENGTH_LONG).show()
                        }
                    })
            }
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}