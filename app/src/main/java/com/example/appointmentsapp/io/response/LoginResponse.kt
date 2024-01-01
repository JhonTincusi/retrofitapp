package com.example.appointmentsapp.io.response

import com.example.appointmentsapp.model.Data

data class LoginResponse(
    val status: Boolean,
    val data: Data,
    val access: String,
    val message: String
)
