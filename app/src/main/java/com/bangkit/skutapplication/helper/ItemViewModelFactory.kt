package com.bangkit.skutapplication.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.skutapplication.view.dailytreatment.AddDailyTreatmentViewModel
import com.bangkit.skutapplication.view.dailytreatment.DailyTreatmentViewModel

class ItemViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: ItemViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ItemViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ItemViewModelFactory::class.java) {
                    INSTANCE = ItemViewModelFactory(application)
                }
            }
            return INSTANCE as ItemViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DailyTreatmentViewModel::class.java)) {
            return DailyTreatmentViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(AddDailyTreatmentViewModel::class.java)) {
            return AddDailyTreatmentViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}