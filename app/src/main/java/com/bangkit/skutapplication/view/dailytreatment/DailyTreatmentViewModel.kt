package com.bangkit.skutapplication.view.dailytreatment

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.skutapplication.model.DailyTreatmentItem
import com.bangkit.skutapplication.repository.DailyTreatmentRepository

class DailyTreatmentViewModel(application: Application) : ViewModel() {
    private val mDailyTreatmentRepository: DailyTreatmentRepository = DailyTreatmentRepository(application)
    fun getAllItem(): LiveData<List<DailyTreatmentItem>> = mDailyTreatmentRepository.getAllItem()

    fun deleteItem(id: Int) = mDailyTreatmentRepository.delete(id)
}