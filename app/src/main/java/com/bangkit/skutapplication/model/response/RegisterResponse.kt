package com.bangkit.skutapplication.model.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("errors")
	val errors: List<ErrorsItem>? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class ErrorsItem(
	@field:SerializedName("message")
	val message: String? = null
)
