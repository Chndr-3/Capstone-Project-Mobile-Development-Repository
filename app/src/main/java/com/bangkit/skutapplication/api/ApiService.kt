package com.bangkit.skutapplication.api

import com.bangkit.skutapplication.model.response.UploadResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("v1/register")
    fun registerUser(

    )

    @FormUrlEncoded
    @POST("upload")
    fun uploadImage(
        @Header("Authorization") token: String,
        @Field("image") image: String
    ): Call<UploadResponse>
}