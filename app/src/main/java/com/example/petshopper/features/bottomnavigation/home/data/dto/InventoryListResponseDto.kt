package com.example.petshopper.features.bottomnavigation.home.data.dto

import com.google.gson.annotations.SerializedName

data class InventoryListResponseDto(
    @SerializedName("items")
    val items: List<PetDto>,

    @SerializedName("page")
    val page: Int,

    @SerializedName("pageSize")
    val pageSize: Int,

    @SerializedName("totalCount")
    val totalCount: Int,

    @SerializedName("hasMore")
    val hasMore: Boolean
)
