package com.example.sns_app

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

class AddPostActivity : AppCompatActivity() {

    private val PICK_IMAGE_FROM_ALBUM: Int by lazy { 0 }
    private var photoUri: Uri? = null

    private val backButton: ImageView by lazy { findViewById(R.id.btn_back) }
    private val addPostButton: ImageView by lazy { findViewById(R.id.btn_add_post) }
    private val addImageButton: ImageView by lazy { findViewById(R.id.btn_add_image) }
    private val addComment: EditText by lazy { findViewById(R.id.et_comment) }

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        addImageButton.setOnClickListener {
            //contentUpload()
            var photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            startActivityForResult(photoPicker, PICK_IMAGE_FROM_ALBUM)
        }

        backButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }


        addPostButton.setOnClickListener {
            if (photoUri != null) {
                contentUpload()
            }else {
                Toast.makeText(applicationContext, R.string.Upload_fail, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun contentUpload() {
        if (photoUri != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle(R.string.Uploading_post)
            progressDialog.setMessage("Processing...")
            progressDialog.show()

            val ref: StorageReference = FirebaseStorage.getInstance().getReference()
                .child(photoUri.toString())

            var user1 = UserTest()
            user1.userName = "Minyong"
            user1.imageUri = photoUri.toString()
            user1.comment = addComment?.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Users").child(user1.userName).child(user1.postNum++.toString())
            database.setValue(user1).addOnSuccessListener {  }

            ref.putFile(photoUri!!).addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, R.string.Upload_success, Toast.LENGTH_LONG).show()
                val test = HomeActivity.getInstance()
                //test!!.createPost(photoUri, addComment?.text.toString())
                test!!.updatePost()
                finish()
            }.addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, R.string.Upload_fail, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_FROM_ALBUM && resultCode == Activity.RESULT_OK && data?.data != null) {
            photoUri = data?.data
            try {
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, photoUri)
                addImageButton.setImageBitmap(bitmap)
            } catch (e: Exception) {
                Log.e("Exception", "Error: " + e)
            }
        }
    }
}