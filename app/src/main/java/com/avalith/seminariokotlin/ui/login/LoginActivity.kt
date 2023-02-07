package com.avalith.seminariokotlin.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.avalith.seminariokotlin.R
import com.avalith.seminariokotlin.databinding.ActivityLoginBinding
import com.avalith.seminariokotlin.extensions.showAlert
import com.avalith.seminariokotlin.extensions.signInWithCredential
import com.avalith.seminariokotlin.ui.home.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.GoogleAuthProvider

const val GOOGLE_SIGN_IN = 100
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this);
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setButton()
    }

    private fun setButton() {
        binding.googleButton.setOnClickListener { setGoogleWidget() }
    }

    private fun setGoogleWidget() {
        val googleConfig = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(this, googleConfig)
        googleClient.signOut()
        startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.let { account ->
                    signInWithCredential(GoogleAuthProvider.getCredential(account.idToken, null))
                        .addOnCompleteListener{
                            if (it.isSuccessful) MainActivity.start(this) else showAlert()
                        }
                }
            } catch (exp: ApiException) {
                showAlert()
            }
        }

    }
}