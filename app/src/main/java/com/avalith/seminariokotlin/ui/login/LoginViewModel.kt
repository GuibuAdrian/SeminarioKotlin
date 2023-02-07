package com.avalith.seminariokotlin.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avalith.seminariokotlin.model.UserData
import com.avalith.seminariokotlin.repositories.FirebaseRepo
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class LoginViewModel: ViewModel() {
    private val repo = FirebaseRepo()

    val onSuccessLiveData = MutableLiveData<UserData>()
    val onErrorLiveData = MutableLiveData<Unit>()

    fun saveInDatabase(user: UserData, account: GoogleSignInAccount) {
        account.id
            ?.let {
                repo.saveUser(user)
                onSuccessLiveData.value = user
            }
            ?: run {
                onErrorLiveData.value = Unit
            }
    }
}