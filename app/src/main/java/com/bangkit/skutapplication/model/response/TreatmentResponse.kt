package com.bangkit.skutapplication.model.response

import com.google.gson.annotations.SerializedName

data class TreatmentResponse(

	@field:SerializedName("results")
	val results: List<ResultsItem>
)

data class ResultsItem(

	@field:SerializedName("treatment_id")
	val treatmentId: String,

	@field:SerializedName("time")
	val time: String,

	@field:SerializedName("product_name")
	val productName: String
)
