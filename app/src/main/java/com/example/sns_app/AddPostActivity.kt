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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

class AddPostActivity : AppCompatActivity() {

    private val PICK_IMAGE_FROM_ALBUM: Int by lazy { 0 }
    private var photoUri: Uri? = null

    private val backButton: ImageButton by lazy { findViewById(R.id.btn_back) }
    private val addPostButton: ImageButton by lazy { findViewById(R.id.btn_add_post) }
    private val addImageButton: ImageView by lazy { findViewById(R.id.btn_add_image) }
    private val addComment: EditText by lazy { findViewById(R.id.et_comment) }

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
            progressDialog.setTitle(R.string.Uploading_image)
            progressDialog.setMessage("Processing...")
            progressDialog.show()

            val ref: StorageReference = FirebaseStorage.getInstance().getReference()
                .child("User1/test")

            ref.putFile(photoUri!!).addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, R.string.Upload_success, Toast.LENGTH_LONG).show()
                val test = HomeActivity.getInstance()
                test!!.createPost(photoUri, addComment?.text.toString())
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