package com.bangkit.skutapplication.model.response

import com.google.gson.annotations.SerializedName

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

data class ListHistoryFaceItem(

	@field:SerializedName("img_link")
	val imgLink: String? = null,

	@field:SerializedName("scan_id")
	val scanId: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null
)
