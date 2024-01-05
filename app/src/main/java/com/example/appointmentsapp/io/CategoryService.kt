package com.example.appointmentsapp.io

import com.example.appointmentsapp.io.response.CategoryResponse
import retrofit2.Call
import retrofit2.http.GET

interface CategoryService {
    @GET("warehouse/categories/filter/")
    fun getCategories(): Call<CategoryResponse>
}