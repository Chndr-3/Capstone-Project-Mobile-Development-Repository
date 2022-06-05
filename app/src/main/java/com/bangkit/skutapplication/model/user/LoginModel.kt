package com.bangkit.skutapplication.model.user

import com.google.gson.annotations.SerializedName

data class LoginModel(
    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String
)
