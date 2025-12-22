package com.example.petshopper.features.bottomnavigation.home.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.petshopper.features.bottomnavigation.home.data.local.entity.PetEntity

@Dao
interface InventoryDao {

    @Query("SELECT * FROM pets WHERE categoryId = :categoryId")
    suspend fun getPetsByCategory(categoryId: String): List<PetEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPets(pets: List<PetEntity>)

    @Query("DELETE FROM pets")
    suspend fun clearPets()

    @Query("DELETE FROM pets WHERE categoryId = :categoryId")
    suspend fun deletePetsByCategoryId(categoryId: String)
}