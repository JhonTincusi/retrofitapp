package com.example.appointmentsapp.io.response

data class CategoryResponse(
    val data: List<Category>,
    val message: String,
    val status: Boolean
)

data class Category(
    val category_id: Int,
    val created: String,
    val modified: String,
    val status: String,
    val code: String,
    val description: String,
    val order: Int
)