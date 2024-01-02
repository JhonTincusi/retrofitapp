package com.example.appointmentsapp.io

import com.example.appointmentsapp.io.response.LoginResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Body

interface ApiService {
    @POST("authentication/")
    fun postLogin(@Body loginRequest: LoginRequest): Call<LoginResponse>


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