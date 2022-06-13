package com.bangkit.skutapplication.api

import com.bangkit.skutapplication.model.DeleteTreatment
import com.bangkit.skutapplication.model.PostTreatment
import com.bangkit.skutapplication.model.response.Dashboard
import com.bangkit.skutapplication.model.response.LoginResponse
import com.bangkit.skutapplication.model.response.RegisterResponse
import com.bangkit.skutapplication.model.response.TreatmentResponse
import com.bangkit.skutapplication.model.response.UploadResponse
import com.bangkit.skutapplication.model.user.DeleteHistory
import com.bangkit.skutapplication.model.user.LoginModel
import com.bangkit.skutapplication.model.user.RegisterModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("upload")
    fun uploadImage(
        @Header("Authorization") token: String,
        @Body json: RequestBody
    ): Call<UploadResponse>

    @POST("login")
    fun loginUser(
        @Body loginModel: LoginModel
    ): Call<LoginResponse>

    @POST("register")
    fun registerUser(
        @Body registerModel: RegisterModel
    ): Call<RegisterResponse>

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

    @HTTP(method = "DELETE", path = "upload", hasBody = true)
    fun deleteHistory(
        @Header("Authorization") token: String,
        @Body scan_id : DeleteHistory
    ) : Call<String>
}