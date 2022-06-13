package com.bangkit.skutapplication.model

data class DailyRoutine(
    var dailyRoutine: String,
    var dailyRoutineIcon: Int,
    var productPrice: String? = null,
    var url: String? = null,
)