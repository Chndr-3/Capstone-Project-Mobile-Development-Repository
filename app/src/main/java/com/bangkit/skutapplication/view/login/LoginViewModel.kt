package com.bangkit.skutapplication.view.login

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.bangkit.skutapplication.api.ApiConfig
import com.bangkit.skutapplication.datastore.UserPreference
import com.bangkit.skutapplication.model.User
import com.bangkit.skutapplication.model.response.LoginResponse
import com.bangkit.skutapplication.model.user.LoginModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> = _loginResult

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun loginUser(loginModel: LoginModel) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().loginUser(loginModel)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _isSuccess.value = true
                    _loginResult.value = response.body()
//                    _loginResult.value = response.body()?.loginResult
                } else {
                    _isSuccess.value = false
                    Log.d("error", response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isSuccess.value = false
                _isLoading.value = false
                Log.d(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun login(token: String) {
        viewModelScope.launch {
            pref.login(token)
        }
    }
}