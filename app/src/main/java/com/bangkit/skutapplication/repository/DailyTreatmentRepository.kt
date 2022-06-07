package com.bangkit.skutapplication.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.bangkit.skutapplication.database.DailyTreatmentDao
import com.bangkit.skutapplication.database.DailyTreatmentDatabase
import com.bangkit.skutapplication.model.DailyTreatmentItem
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DailyTreatmentRepository(application: Application) {
    private val mDailyTreatmentDao: DailyTreatmentDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = DailyTreatmentDatabase.getDatabase(application)
        mDailyTreatmentDao = db.dailyTreatmentDao()
    }

    fun getAllItem(): LiveData<List<DailyTreatmentItem>> = mDailyTreatmentDao.getAllItem()

    fun insert(dailyTreatmentItem: List<DailyTreatmentItem>) {
        executorService.execute { mDailyTreatmentDao.insert(dailyTreatmentItem) }
    }

    fun delete(id: Int) {
        executorService.execute { mDailyTreatmentDao.delete(id) }
    }

    fun update(dailyTreatmentItem: DailyTreatmentItem) {
        executorService.execute { mDailyTreatmentDao.update(dailyTreatmentItem) }
    }
}