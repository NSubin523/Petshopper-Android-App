package com.example.petshopper.core.domain.model

data class UserModel(
    val uuid: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val createdAt: String
)
