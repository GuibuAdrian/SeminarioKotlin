package com.avalith.seminariokotlin.extensions

import android.app.Activity
import android.app.AlertDialog
import com.avalith.seminariokotlin.R
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth

fun Activity.signInWithCredential(credential: AuthCredential) =
    FirebaseAuth.getInstance().signInWithCredential(credential)

fun Activity.signOut() = FirebaseAuth.getInstance()

fun Activity.showAlert() {
    val builder = AlertDialog.Builder(this)
    builder.setTitle(getString(R.string.error))
    builder.setMessage(getString(R.string.error_message))
    builder.setPositiveButton(getString(R.string.ok), null)
    val dialog = builder.create()
    dialog.show()
}