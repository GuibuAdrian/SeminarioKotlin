package com.avalith.seminariokotlin.extensions

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
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

fun Activity.getPref() = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)

fun Activity.savePref(email: String) {
    val prefs = getPref().edit()
    prefs.putString(R.string.email.toString(), email)
    prefs.apply()
}

fun Activity.getEmailPref(): String? {
    val prefs = getPref()
    return prefs.getString(R.string.email.toString(), null)
}

fun Activity.clearPrefs(){
    val prefs = getPref()
    prefs?.edit()?.clear()?.apply()
}