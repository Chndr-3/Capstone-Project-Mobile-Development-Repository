package com.bangkit.skutapplication.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UploadResponse(

	@field:SerializedName("imgLink")
	val imgLink: String? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("scan_result")
	val scanResult: ScanResult? = null
): Parcelable

@Parcelize
data class User(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("exp")
	val exp: Int? = null,

	@field:SerializedName("iat")
	val iat: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
): Parcelable

@Parcelize
data class ScanResult(

	@field:SerializedName("eksim")
	val eksim: Double? = null,

	@field:SerializedName("normal")
	val normal: Double? = null,

	@field:SerializedName("acne")
	val acne: Double? = null,

	@field:SerializedName("rosacea")
	val rosacea: Double? = null
): Parcelable
