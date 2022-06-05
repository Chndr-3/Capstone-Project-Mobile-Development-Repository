package com.bangkit.skutapplication.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.sql.Time

@Entity(tableName = "item")
@Parcelize
data class DailyTreatmentItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 1,
    @ColumnInfo(name = "product_name")
    var product_name: String? = null,
    @ColumnInfo(name = "time")
    var time: String? = null
) : Parcelable