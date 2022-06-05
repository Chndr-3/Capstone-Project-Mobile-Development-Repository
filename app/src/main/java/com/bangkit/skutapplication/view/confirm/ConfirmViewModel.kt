package com.bangkit.skutapplication.view.confirm

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.skutapplication.api.ApiConfig
import com.bangkit.skutapplication.datastore.UserPreference
import com.bangkit.skutapplication.model.response.UploadResponse
import com.bangkit.skutapplication.model.User
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfirmViewModel(private val pref: UserPreference) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val imageBase64 = MutableLiveData<String>()

    fun setImageBase64(base64Image: String) {
        this.imageBase64.value = base64Image
    }

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun uploadImage(token: String) {

        _isLoading.value = true
        val client = ApiConfig.getApiService().uploadImage("Bearer $token", imageBase64.toString())
        client.enqueue(object : Callback<UploadResponse> {
            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _isError.value = false
//                    _uploadMessage.value = response.body()?.message
                    Log.d("testhahaha", response.body()?.user?.name.toString())
//                    Log.d("base64", base64Image)
                } else {
                    _isError.value = false
                    Log.d("testhehehe", response.body()?.user?.name.toString())
                }
            }
            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                Log.d(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }
}