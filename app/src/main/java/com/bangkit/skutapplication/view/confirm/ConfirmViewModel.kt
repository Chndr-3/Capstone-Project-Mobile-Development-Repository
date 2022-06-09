package com.bangkit.skutapplication.view.confirm

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.skutapplication.api.ApiConfig2
import com.bangkit.skutapplication.datastore.UserPreference
import com.bangkit.skutapplication.model.response.UploadResponse
import com.bangkit.skutapplication.model.User
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfirmViewModel(private val pref: UserPreference) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _scanResult = MutableLiveData<UploadResponse>()
    val scanResult: LiveData<UploadResponse> = _scanResult

    private val imageBase64 = MutableLiveData<String>()

    fun setImageBase64(base64Image: String) {
        this.imageBase64.value = base64Image
    }

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun uploadImage(token: String) {

        val body = getImageBase64Json(imageBase64.value.toString())
            .trimIndent().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        _isLoading.value = true
        val client = ApiConfig2.getApiService().uploadImage("Bearer $token", body)
        client.enqueue(object : Callback<UploadResponse> {
            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _isError.value = false
                    _scanResult.value = response.body()
//                    _uploadMessage.value = response.body()?.message
                    Log.d("testhahaha", _scanResult.value?.user?.name.toString())
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

    private fun getImageBase64Json(imageData: String): String{
        val json = JSONObject()
        json.put("image", imageData)
        return json.toString(4)
    }
}