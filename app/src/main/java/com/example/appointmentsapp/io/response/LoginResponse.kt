package com.example.appointmentsapp.io.response

import com.example.appointmentsapp.model.User

data class LoginResponse(
    val status: Boolean,
    val user: User,
    val access: String

)
