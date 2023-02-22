package com.avalith.seminariokotlin.ui.post

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.avalith.seminariokotlin.databinding.ActivityPostBinding
import com.avalith.seminariokotlin.extensions.*
import com.avalith.seminariokotlin.ui.dialog.LoadingDialog
import com.squareup.picasso.Picasso

class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding
    private val viewModel by lazy { ViewModelProvider(this).get(PostViewModel::class.java) }
    private val loadingDialog = LoadingDialog()

    private val name = System.currentTimeMillis().toString()

    companion object {
        const val CAMERA_REQUEST = 100
        const val GALLERY_REQUEST = 120

        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, PostActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setButtons()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        viewModel.imageLiveData.observe(this) { setImage(it) }
        viewModel.okLiveData.observe(this) { finish() }
        viewModel.errorLiveData.observe(this) { showAlert() }
    }

    private fun setImage(uri: Uri) {
        Picasso.get().load(uri).into(binding.imageView)
        setVisibility()
    }

    private fun setVisibility() {
        loadingDialog.dismiss()
        with(binding) {
            buttonsLayout.visibility = View.GONE
            imageLayout.visibility = View.VISIBLE
            uploadButton.visibility = View.VISIBLE
        }
    }

    private fun setButtons() {
        with(binding) {
            cameraButton.setOnClickListener { setCameraWidget() }
            galleryButton.setOnClickListener { setGalleryWidget() }
            uploadButton.setOnClickListener { validateAndPush() }
        }
    }

    private fun validateAndPush() {
        if (binding.editTextDescription.text.toString().isNotEmpty()) {
            loadingDialog.show(supportFragmentManager)
            viewModel.createPost(
                email = getEmailPref()!!,
                description = binding.editTextDescription.text.toString(),
                name
            )

        } else {
            Toast.makeText(this, "Must write a description", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setGalleryWidget() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/"
        startActivityForResult(intent, GALLERY_REQUEST)
    }

    private fun setCameraWidget() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_CANCELED) return
        when(requestCode) {
            CAMERA_REQUEST -> { setSelectedBitmap(data?.extras?.get("data") as Bitmap) }  //We need to transform whatever it receives into a file
            GALLERY_REQUEST -> { setSelectedUri(data?.data) }
        }
    }

    private fun setSelectedBitmap(bitmap: Bitmap?) {
        val uri = bitmap?.let { bitmapToUri(it, name) }
        setSelectedUri(uri)
    }

    private fun setSelectedUri(uri: Uri?) {
        loadingDialog.show(supportFragmentManager)
        uri?.let {
            val extension =getFileExt(it)?:"png"
            viewModel.saveToDB("$name.$extension", it)
        }
    }
}