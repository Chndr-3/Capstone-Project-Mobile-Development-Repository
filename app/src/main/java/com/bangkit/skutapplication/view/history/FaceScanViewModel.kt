package com.bangkit.skutapplication.view.history

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.bangkit.skutapplication.api.ApiConfig
import com.bangkit.skutapplication.datastore.UserPreference
import com.bangkit.skutapplication.model.DeleteTreatment
import com.bangkit.skutapplication.model.User
import com.bangkit.skutapplication.model.response.Dashboard
import com.bangkit.skutapplication.model.response.ListHistoryFaceItem
import com.bangkit.skutapplication.model.user.DeleteHistory
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FaceScanViewModel(private val pref: UserPreference) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _isSuccess1 = MutableLiveData<Boolean>()
    val isSuccess1: LiveData<Boolean> = _isSuccess1

    private val _name = MutableLiveData<String>()
    val name : LiveData<String> = _name

    private val _listHistory = MutableLiveData<List<ListHistoryFaceItem>>()
    val listHistory: LiveData<List<ListHistoryFaceItem>> = _listHistory
    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }
    fun getItem(token: String) {
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
                    _name.value = response.body()!!.name!!
                    _listHistory.value = (response.body()?.listHistoryFace as List<ListHistoryFaceItem>?)!!
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
    fun deleteHistory(token: String, item: DeleteHistory) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().deleteHistory(token, item)
        client.enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _isSuccess1.value = true
//
                } else {
                    _isSuccess1.value = false
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                _isSuccess1.value = false
                _isLoading.value = false
                Log.d(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }
}