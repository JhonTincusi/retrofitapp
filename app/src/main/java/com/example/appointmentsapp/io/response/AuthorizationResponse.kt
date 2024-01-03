package com.example.appointmentsapp.io.response

data class AuthorizationResponse(
    val data: List<Profile>,
    val message: String,
    val status: Boolean
)

data class Profile(
    val profile_id: Int,
    val systems: List<System>,
)

data class System(
    val system_id: Int,
    val modules: List<Module>,
)

data class Module(
    val module_id: Int,
    val name: String,
    val url: String,
)