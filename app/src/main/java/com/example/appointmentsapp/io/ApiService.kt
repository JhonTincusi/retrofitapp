package com.example.appointmentsapp.io

import com.example.appointmentsapp.io.response.AuthorizationResponse
import com.example.appointmentsapp.io.response.CategoryResponse
import com.example.appointmentsapp.io.response.LoginResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @POST("authentication/")
    fun postLogin(@Body loginRequest: LoginRequest): Call<LoginResponse>
    @GET("master/authorization/")
    fun getUserAuthorization(@Header("Authorization") authToken: String): Call<AuthorizationResponse>

    @GET("warehouse/categories/filter/")
    fun getCategories(@Header("Authorization") authToken: String): Call<CategoryResponse>

    companion object Factory {
        private const val BASE_URL = "http://10.0.2.2:8000/api/"
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }

    data class LoginRequest(
        val username: String,
        val password: String
    )
}