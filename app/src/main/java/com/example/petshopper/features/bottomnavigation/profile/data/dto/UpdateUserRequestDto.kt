package com.example.petshopper.features.bottomnavigation.profile.data.dto

import com.google.gson.annotations.SerializedName

data class UpdateUserRequestDto(
    @SerializedName("firstName") val firstName: String? = null,
    @SerializedName("lastName") val lastName: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("phone") val phoneNumber: String? = null,
)
