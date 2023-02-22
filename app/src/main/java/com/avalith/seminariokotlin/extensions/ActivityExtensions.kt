package com.avalith.seminariokotlin.extensions

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.core.net.toUri
import com.avalith.seminariokotlin.R
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

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

//turn bitmap into uri
fun Activity.bitmapToUri(bitmap: Bitmap, name: String): Uri {
    val file = File(this.cacheDir, name)    // Get access to a local file
    file.delete()   //Delete the File, just in case, that there was still another File
    file.createNewFile()
    val fileOutputStream = file.outputStream()
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    fileOutputStream.write(byteArray)
    fileOutputStream.flush()
    fileOutputStream.close()
    byteArrayOutputStream.close()

    return file.toUri() // URI is where the file is in the cellphone
}

fun Activity.getFileExt(uri: Uri): String? {
    val contextResolver = contentResolver
    val mimeTypeMap = MimeTypeMap.getSingleton()
    return mimeTypeMap.getExtensionFromMimeType(contextResolver.getType(uri))
}

fun Activity.getDate(): String {
    val cal = Calendar.getInstance()
    val currentDate = SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault())
    val date = currentDate.format(cal.time)
    val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val time = currentTime.format(cal.time)
    return "$date:$time"
}