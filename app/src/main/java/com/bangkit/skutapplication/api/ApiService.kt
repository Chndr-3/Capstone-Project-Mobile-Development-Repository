package com.bangkit.skutapplication.api

import com.bangkit.skutapplication.model.response.LoginResponse
import com.bangkit.skutapplication.model.response.UploadResponse
import com.bangkit.skutapplication.model.user.LoginModel
import retrofit2.Call
import retrofit2.http.*

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

    @POST("login")
    fun loginUser(
//        @Field("email") email: String,
//        @Field("password") password: String
        @Body loginModel: LoginModel
    ): Call<LoginResponse>
}