package com.bangkit.skutapplication.view.dailytreatment

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.skutapplication.api.ApiConfig
import com.bangkit.skutapplication.model.DailyTreatmentItem
import com.bangkit.skutapplication.model.DeleteTreatment
import com.bangkit.skutapplication.model.response.Dashboard
import com.bangkit.skutapplication.model.response.ListDailyTreatmentItem
import com.bangkit.skutapplication.repository.DailyTreatmentRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DailyTreatmentViewModel(application: Application) : ViewModel() {
    private val mDailyTreatmentRepository: DailyTreatmentRepository = DailyTreatmentRepository(application)
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private var _skincareRoutineItem = MutableLiveData<List<ListDailyTreatmentItem>>()
    var skincareRoutineItem : LiveData<List<ListDailyTreatmentItem>> = _skincareRoutineItem
    fun getAllItem(): LiveData<List<DailyTreatmentItem>> = mDailyTreatmentRepository.getAllItem()

    fun deleteItem(id: Int) = mDailyTreatmentRepository.delete(id)
    fun insert(item: List<DailyTreatmentItem>) {
        mDailyTreatmentRepository.insert(item)
    }
    fun showItem(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDashboard(token)
        client.enqueue(object : Callback<Dashboard> {
            override fun onResponse(
                call: Call<Dashboard>,
                response: Response<Dashboard>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _isSuccess.value = true
                    _skincareRoutineItem.value = (response.body()?.listDailyTreatment as List<ListDailyTreatmentItem>?)!!
//
                } else {
                    _isSuccess.value = false
                }
            }

            override fun onFailure(call: Call<Dashboard>, t: Throwable) {
                _isSuccess.value = false
                _isLoading.value = false
                Log.d(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun deleteItem(token: String, item: DeleteTreatment) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().deleteTreatment(token, item)
        client.enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                _isLoading.value = false
                _isSuccess.value = response.isSuccessful
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                _isSuccess.value = false
                _isLoading.value = false
                Log.d(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }
}