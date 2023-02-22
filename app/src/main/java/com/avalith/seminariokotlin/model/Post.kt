package com.avalith.seminariokotlin.model

import java.util.Calendar

data class Post(
    var userName: String?= null,
    var userImage: String?= null,
    var image: String?=null,
    var description: String?=null,
    var date: String?=null,
    var timestamp: Long?=null
) {
    fun map(date: String, description: String, url: String, user: UserData) = Post(
        date = date,
        userName = user.name,
        userImage = user.photo,
        description = description,
        image = url,
        timestamp = Calendar.getInstance().timeInMillis
    )
}
