package com.bangkit.skutapplication.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("v1/register")
    fun registerUser(

    )
}