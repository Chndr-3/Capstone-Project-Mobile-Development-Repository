package com.bangkit.skutapplication.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Dashboard(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("listHistoryFace")
	val listHistoryFace: List<ListHistoryFaceItem?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("listDailyTreatment")
	val listDailyTreatment: List<ListDailyTreatmentItem?>? = null
)
@Parcelize
data class ListHistoryFaceItem(

	@field:SerializedName("eksim")
	val eksim: Double? = null,

	@field:SerializedName("normal")
	val normal: Double? = null,

	@field:SerializedName("acne")
	val acne: Double? = null,

	@field:SerializedName("img_link")
	val imgLink: String? = null,

	@field:SerializedName("rosacea")
	val rosacea: Double? = null,

	@field:SerializedName("scan_id")
	val scanId: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null
) : Parcelable

data class ListDailyTreatmentItem(

	@field:SerializedName("treatment_id")
	val treatmentId: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("product_name")
	val productName: String? = null
)
