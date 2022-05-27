package com.bangkit.skutapplication.view.dailytreatment

import android.app.Application
import androidx.lifecycle.ViewModel
import com.bangkit.skutapplication.model.DailyTreatmentItem
import com.bangkit.skutapplication.repository.DailyTreatmentRepository

class AddDailyTreatmentViewModel(application: Application) : ViewModel(){private val mDailyTreatmentRepository: DailyTreatmentRepository = DailyTreatmentRepository(application)

    fun insert(item: DailyTreatmentItem) {
        mDailyTreatmentRepository.insert(item)
    }
}