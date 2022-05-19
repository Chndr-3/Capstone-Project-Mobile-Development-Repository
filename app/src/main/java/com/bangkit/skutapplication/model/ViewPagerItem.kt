package com.bangkit.skutapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ViewPagerItem(
    var diseaseName: String,
    var diseaseDescription: String,
) : Parcelable