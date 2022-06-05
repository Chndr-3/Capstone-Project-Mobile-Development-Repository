package com.bangkit.skutapplication.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("token")
	val token: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
