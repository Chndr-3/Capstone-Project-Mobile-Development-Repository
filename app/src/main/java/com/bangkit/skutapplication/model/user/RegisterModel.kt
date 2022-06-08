package com.bangkit.skutapplication.model.user

import com.google.gson.annotations.SerializedName

data class RegisterModel(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("password2")
    val password2: String
)
