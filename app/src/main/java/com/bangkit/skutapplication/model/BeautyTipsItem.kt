package com.bangkit.skutapplication.model

data class BeautyTipsItem(
    var number: Int,
    var beautyTips: String,
    var beautyDescription: String,
    var isVisible: Boolean = false
)