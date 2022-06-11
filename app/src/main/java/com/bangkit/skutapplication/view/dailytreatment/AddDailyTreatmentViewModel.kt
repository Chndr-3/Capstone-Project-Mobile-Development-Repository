package com.bangkit.skutapplication.view.dailytreatment

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.skutapplication.api.ApiConfig
import com.bangkit.skutapplication.model.PostTreatment
import com.bangkit.skutapplication.model.response.ResultsItem
import com.bangkit.skutapplication.model.response.TreatmentResponse
import com.bangkit.skutapplication.repository.DailyTreatmentRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddDailyTreatmentViewModel : ViewModel(){
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess
    private val _id = MutableLiveData<List<ResultsItem>>()
    val id : LiveData<List<ResultsItem>> = _id
    fun postSkincareRoutine(token: String, model: PostTreatment) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().postTreatment(token, model)
        client.enqueue(object : Callback<TreatmentResponse> {
            override fun onResponse(
                call: Call<TreatmentResponse>,
                response: Response<TreatmentResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _isSuccess.value = true
                        _id.value = responseBody.results
                    }
                } else {
                    _isSuccess.value = false
                }
            }
            override fun onFailure(call: Call<TreatmentResponse>, t: Throwable) {
                _isLoading.value = false
                _isSuccess.value = false
            }
        })
    }
}