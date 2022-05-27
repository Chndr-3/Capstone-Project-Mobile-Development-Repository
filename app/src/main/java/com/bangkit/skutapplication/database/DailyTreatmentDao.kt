package com.bangkit.skutapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bangkit.skutapplication.model.BeautyTipsItem
import com.bangkit.skutapplication.model.DailyTreatmentItem

@Dao
interface DailyTreatmentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: DailyTreatmentItem)
    @Update
    fun update(item: DailyTreatmentItem)
    @Delete
    fun delete(item: DailyTreatmentItem)
    @Query("SELECT * from item ORDER BY id ASC")
    fun getAllItem(): LiveData<List<DailyTreatmentItem>>
}