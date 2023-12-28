package com.example.sns_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout

class AddPostActivity : AppCompatActivity() {

    private val backButton: ImageButton by lazy { findViewById(R.id.btn_back) }
    private val addPostButton: ImageButton by lazy { findViewById(R.id.btn_add_post) }
    private val addImageButton: ImageButton by lazy { findViewById(R.id.btn_add_image) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        backButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        addImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            startActivityForResult(intent, 12)
        }

        addPostButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            
        }
    }
    private fun createPost() : View {
        val post = Button(this)
        val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300)

        post.layoutParams = lp

        return post
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
    }
}