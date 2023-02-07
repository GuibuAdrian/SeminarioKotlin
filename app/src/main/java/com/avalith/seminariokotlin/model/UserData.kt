package com.avalith.seminariokotlin.model

import com.google.firebase.auth.FirebaseUser

data class UserData(
    var name: String?= null,
    var email: String?= null,
    var photo: String?= null
) {
    fun map(user:FirebaseUser): UserData {
        this.name = user.displayName
        this.email = user.email
        this.photo = user.photoUrl.toString()
        return this
    }
}
