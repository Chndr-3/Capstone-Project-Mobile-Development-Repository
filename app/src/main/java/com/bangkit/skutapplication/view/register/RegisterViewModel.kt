package com.bangkit.skutapplication.view.register

import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.api.ApiConfig
import com.bangkit.skutapplication.model.response.ErrorsItem
import com.bangkit.skutapplication.model.response.LoginResponse
import com.bangkit.skutapplication.model.response.RegisterResponse
import com.bangkit.skutapplication.model.user.LoginModel
import com.bangkit.skutapplication.model.user.RegisterModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel(){
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _registerResult = MutableLiveData<RegisterResponse>()
    val registerResult: LiveData<RegisterResponse> = _registerResult

    private val _registerError = MutableLiveData<List<ErrorsItem>>()
    val registerError: LiveData<List<ErrorsItem>> = _registerError

    fun registerUser(registerModel: RegisterModel) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().registerUser(registerModel)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _isSuccess.value = true
                    _registerResult.value = response.body()
                } else {
                    _isSuccess.value = false
//                    val items = response.body()?.errors
//                    Log.d("error123", _registerError.value.toString())
//                    Log.d("error123", "masuk sini")
//                    if (items != null) {
//                        for (i in 0 until _registerError.value!!.count()) {
//                            // Message
//                            val message = _registerError.value!![i].message ?: "N/A"
//                            Log.d("error2: ", message)
//                        }
//                    }
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isSuccess.value = false
                _isLoading.value = false
                Log.d(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }
}