package com.bangkit.skutapplication.api

import com.bangkit.skutapplication.model.DailyTreatmentItem
import com.bangkit.skutapplication.model.DeleteTreatment
import com.bangkit.skutapplication.model.PostTreatment
import com.bangkit.skutapplication.model.response.Dashboard
import com.bangkit.skutapplication.model.response.LoginResponse
import com.bangkit.skutapplication.model.response.TreatmentResponse
import com.bangkit.skutapplication.model.response.UploadResponse
import com.bangkit.skutapplication.model.user.LoginModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("v1/register")
    fun registerUser(

    )

    @POST("upload")
    fun uploadImage(
        @Header("Authorization") token: String,
//        @Field("image") image: String
        @Body json: RequestBody
    ): Call<UploadResponse>

    @POST("login")
    fun loginUser(
//        @Field("email") email: String,
//        @Field("password") password: String
        @Body loginModel: LoginModel
    ): Call<LoginResponse>

    @GET("dashboard")
    fun getDashboard(
        @Header("Authorization") token: String,
    ): Call<Dashboard>


    @POST("treatment")
    fun postTreatment(
        @Header("Authorization") token: String,
        @Body postTreatment: PostTreatment
    ) : Call<TreatmentResponse>

    @HTTP(method = "DELETE", path = "treatment", hasBody = true)
    fun deleteTreatment(
        @Header("Authorization") token: String,
        @Body treatment_id: DeleteTreatment
    ) : Call<String>
}