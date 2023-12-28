package com.example.sns_app

import java.io.Serializable

class User(
    var email: String,
    var pw: String,
    var name: String
): Serializable {
    constructor(): this("", "", "")
}