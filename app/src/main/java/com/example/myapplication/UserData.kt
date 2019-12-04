package com.example.myapplication

import java.io.Serializable

class UserData (
    val uid: String? = "",
    val username: String? = "",
    val email : String? = ""
) : Serializable