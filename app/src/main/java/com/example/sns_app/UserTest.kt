package com.example.sns_app

import android.net.Uri
import java.io.Serializable

data class UserTest(
    var userName: String,
    var imageUri: String,
    var comment: String,
    var postNum: Int

    ): Serializable {
    constructor(): this("", "", "", 1)
}