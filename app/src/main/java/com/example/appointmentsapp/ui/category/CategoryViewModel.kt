package com.example.appointmentsapp.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appointmentsapp.io.CategoryService
import com.example.appointmentsapp.io.response.Category
import com.example.appointmentsapp.io.response.CategoryResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CategoryViewModel : ViewModel() {
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    fun fetchCategories() {
        // Assuming Retrofit has already been set up with a base URL
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CategoryService::class.java)
        service.getCategories().enqueue(object : retrofit2.Callback<CategoryResponse> {
            override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
                if (response.isSuccessful) {
                    _categories.value = response.body()?.data
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }
}