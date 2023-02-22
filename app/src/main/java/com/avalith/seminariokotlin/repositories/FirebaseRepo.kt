package com.avalith.seminariokotlin.repositories

import android.net.Uri
import com.avalith.seminariokotlin.model.Post
import com.avalith.seminariokotlin.model.UserData
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class FirebaseRepo {
    private val userDb = Firebase.firestore

    private val database = FirebaseDatabase.getInstance()

    private val storageReference = FirebaseStorage.getInstance().getReference("Post Images")

    private val postReference = database.getReference("posts")

    fun saveUser(user: UserData) {
        userDb.collection("users").document(user.email?:"").set(
            hashMapOf(
                "name" to user.name,
                "email" to user.email,
                "photo" to user.photo.toString()
            )
        )
    }

    fun getUser(email: String) = userDb.collection("users").document(email).get()

    fun getPost() = database.getReference("posts")

    fun pushPost(post: Post) {
        postReference.child("${post.date}:${post.userName}".lowercase(Locale.getDefault()))
            .setValue(post)
    }

    fun getStorageReference() = storageReference

    fun saveImageToDb(reference: StorageReference, uri: Uri) = reference.putFile(uri)
}