package com.avalith.seminariokotlin.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.avalith.seminariokotlin.databinding.ItemPostBinding
import com.avalith.seminariokotlin.model.Post
import com.squareup.picasso.Picasso

class PostAdapter(private val postList: List<Post>): RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemPostBinding ): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ViewHolder {
        val binding = ItemPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: PostAdapter.ViewHolder, position: Int) {
        with(holder) {
            with(postList[position]) {
                binding.userNameTextView.text = userName
                binding.dateTextView.text = date
                binding.descriptionTextView.text = description
                Picasso.get().load(Uri.parse(userImage)).into(binding.profileImageView)
                Picasso.get().load(Uri.parse(image)).into(binding.image)
            }
        }
    }

    override fun getItemCount() = postList.size
}