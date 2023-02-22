package com.avalith.seminariokotlin.ui.post

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avalith.seminariokotlin.model.Post
import com.avalith.seminariokotlin.model.UserData
import com.avalith.seminariokotlin.repositories.FirebaseRepo
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class PostViewModel: ViewModel() {
    private var repository = FirebaseRepo()
    private lateinit var reference: StorageReference

    var imageLiveData = MutableLiveData<Uri>()
    var okLiveData = MutableLiveData<Unit>()
    var errorLiveData = MutableLiveData<Unit>()

    private lateinit var uploadTask: UploadTask
    private lateinit var urlTask: Task<Uri>

    fun createPost(email: String, description: String, date: String) {
        repository.getUser(email).addOnCompleteListener{
            if (it.isSuccessful) {
                val user = it.result.toObject(UserData::class.java)
                user?.let { pushToDb(date, description, getUrl(), it) }
                    ?:run { errorLiveData.value = Unit }

            } else {
                errorLiveData.value = Unit
            }
        }
    }

    private fun pushToDb(date: String, description: String, url: String, user: UserData) {
        val post = Post().map(date, description, url, user)
        repository.pushPost(post)
        okLiveData.value = Unit
    }

    fun saveToDB(name: String, uri: Uri) {
        uploadTask = saveImageToDB(name, uri)
        urlTask = uploadTask.continueWithTask { reference.downloadUrl }
        uploadTask.addOnCompleteListener{ imageLiveData.value = uri }
    }

    private fun saveImageToDB(name: String, uri: Uri): UploadTask {
        reference = repository.getStorageReference().child(name)
        return repository.saveImageToDb(reference, uri)
    }

    private fun getUrl(): String =
        if (urlTask.isComplete and urlTask.isSuccessful) urlTask.result.toString() else "no data yet"
}