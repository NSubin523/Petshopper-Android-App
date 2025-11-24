package com.example.petshopper.features.bottomnavigation.home.data.dto

import com.google.gson.annotations.SerializedName

data class PetDto(

    @SerializedName("uuid")
    val uuid: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("imageUrl")
    val imageUrl: String,

    @SerializedName("zipCode")
    val zipCode: String,

    @SerializedName("priceMin")
    val priceMin: Double,

    @SerializedName("priceMax")
    val priceMax: Double,

    @SerializedName("age")
    val age: Int,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("availability")
    val availability: String
)