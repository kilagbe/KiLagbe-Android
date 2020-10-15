package com.kichai.kichai.data

data class User(var name: String, var email: String, val phone: String) {
    var uid: String? = null
    constructor():this("", "", "")
}