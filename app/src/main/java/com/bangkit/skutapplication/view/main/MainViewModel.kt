package com.bangkit.skutapplication.view.main

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.bangkit.skutapplication.api.ApiConfig
import com.bangkit.skutapplication.datastore.UserPreference
import com.bangkit.skutapplication.model.User
import com.bangkit.skutapplication.model.response.Dashboard
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: UserPreference) :ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess
    private val _name = MutableLiveData<String>()
    val name : LiveData<String> = _name

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
    fun saveUser(user: String) {
        viewModelScope.launch {
            pref.saveUsername(user)
        }
    }
}
