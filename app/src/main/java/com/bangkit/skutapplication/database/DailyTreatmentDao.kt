package com.bangkit.skutapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bangkit.skutapplication.model.BeautyTipsItem
import com.bangkit.skutapplication.model.DailyTreatmentItem

@Dao
interface DailyTreatmentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: List<DailyTreatmentItem>)
    @Update
    fun update(item: DailyTreatmentItem)
    @Query("DELETE FROM item WHERE id = :id ")
    fun delete(id: Int)
    @Query("SELECT * from item ORDER BY id ASC")
    fun getAllItem(): LiveData<List<DailyTreatmentItem>>
}