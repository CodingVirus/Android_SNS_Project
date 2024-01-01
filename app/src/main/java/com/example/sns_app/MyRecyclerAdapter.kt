package com.example.sns_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class MyRecyclerAdapter(private val dataList: ArrayList<UserTest>): RecyclerView.Adapter<MyRecyclerAdapter.ViewHolderClass>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_recycler_item, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = dataList[position]
        holder.postImg.setImageURI(currentItem.imageUri.toUri())
        holder.comment.text = currentItem.comment
        holder.userName.text = currentItem.userName
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postImg: ImageView = itemView.findViewById(R.id.main_post_image)
        val comment: TextView = itemView.findViewById(R.id.txt_comment)
        val userName: TextView = itemView.findViewById(R.id.txt_user_name)
    }
}