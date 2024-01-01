package com.example.sns_app

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class EditProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        auth = Firebase.auth
        firestore = FirebaseFirestore.getInstance()
        val uid = auth.currentUser?.uid
        val email = intent.getStringExtra("email").toString()
        val name = intent.getStringExtra("name").toString()

        val inputEmail = findViewById<EditText>(R.id.et_inputEmail)
        val inputPw = findViewById<EditText>(R.id.et_inputPw)
        val inputPwValid = findViewById<EditText>(R.id.et_inputPwValid)
        val inputName = findViewById<EditText>(R.id.et_inputName)
        val submitButton = findViewById<Button>(R.id.btn_submit)
        val backButton = findViewById<ImageView>(R.id.btn_back)

        inputEmail.setText(email)
        inputName.setText(name)

        submitButton.setOnClickListener {

            val db = firestore.collection("users")

            if (inputPw.text.isEmpty() || inputPwValid.text.isEmpty()) {
                Toast.makeText(this, R.string.not_input_text, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (inputName.text.isNotEmpty() && inputEmail.text.isNotEmpty() && inputPw.text.isNotEmpty() && inputPwValid.text.isNotEmpty()) {
                db.get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            for (document in task.result) {
                                Log.d(ContentValues.TAG, document.id + " => " + document.data)
                                val dataMap = document.data
                                if (dataMap["uid"] == auth?.currentUser?.uid) {
                                    val docID = document.id
                                    val name = inputName.text.toString()
                                    val password = inputPw.text.toString()
                                    Log.d(ContentValues.TAG, "document id : $docID")
                                    db.document("$docID")
                                        .update("name", "$name")
                                        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
                                        .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }

                                    val user = Firebase.auth.currentUser
                                    user!!.updateEmail("user@example.com")
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Log.d(TAG, "User email address updated.")
                                            }
                                        }
                                    user!!.updatePassword(password)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Log.d(TAG, "User password updated.")
                                            }
                                        }
                                }
                            }
                            finish()
                        } else {
                            Log.w(ContentValues.TAG, "Error => ", task.exception)
                        }
                    }
            }

            if (inputName.text.isNotEmpty() && inputEmail.text.isNotEmpty() && inputPw.text.isEmpty() && inputPwValid.text.isEmpty()) {
                db.get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            for (document in task.result) {
                                Log.d(ContentValues.TAG, document.id + " => " + document.data)
                                val dataMap = document.data
                                if (dataMap["uid"] == auth?.currentUser?.uid) {
                                    val docID = document.id
                                    val name = inputName.text.toString()
                                    val email = inputEmail.text.toString()
                                    val userStack = User(name, email)
                                    Log.d(ContentValues.TAG, "document id : $docID")
                                    db.document("$docID")
                                        .set(userStack)
                                        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
                                        .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }

                                    val user = Firebase.auth.currentUser
                                    user!!.updateEmail("user@example.com")
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Log.d(TAG, "User email address updated.")
                                            }
                                        }

                                }
                            }
                            finish()
                        } else {
                            Log.w(ContentValues.TAG, "Error => ", task.exception)
                        }
                    }
            }

            if (inputName.text.isNotEmpty() && inputEmail.text.isEmpty() && inputPw.text.isEmpty() && inputPwValid.text.isEmpty()) {
                db.get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            for (document in task.result) {
                                Log.d(ContentValues.TAG, document.id + " => " + document.data)
                                val dataMap = document.data
                                if (dataMap["uid"] == auth?.currentUser?.uid) {
                                    val docID = document.id
                                    val name = inputName.text.toString()
                                    Log.d(ContentValues.TAG, "document id : $docID")
                                    db.document("$docID")
                                        .update("name", "$name")
                                        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
                                        .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
                                }
                            }
                            finish()
                        } else {
                            Log.w(ContentValues.TAG, "Error => ", task.exception)
                        }
                    }
            }

            if (inputName.text.isEmpty() && inputEmail.text.isNotEmpty() && inputPw.text.isEmpty() && inputPwValid.text.isEmpty()) {
                db.get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            for (document in task.result) {
                                Log.d(ContentValues.TAG, document.id + " => " + document.data)
                                val dataMap = document.data
                                if (dataMap["uid"] == auth?.currentUser?.uid) {
                                    val docID = document.id
                                    val email = inputEmail.text.toString()

                                    Log.d(ContentValues.TAG, "document id : $docID")
                                    db.document("$docID")
                                        .update("email", email)
                                        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
                                        .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }

                                    val user = Firebase.auth.currentUser
                                    user!!.updateEmail("user@example.com")
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Log.d(TAG, "User email address updated.")
                                            }
                                        }

                                }
                            }
                            finish()
                        } else {
                            Log.w(ContentValues.TAG, "Error => ", task.exception)
                        }
                    }
            }
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}