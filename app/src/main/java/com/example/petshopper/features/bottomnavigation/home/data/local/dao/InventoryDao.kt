package com.example.petshopper.features.bottomnavigation.home.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.petshopper.features.bottomnavigation.home.data.local.entity.PetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {

    @Query("SELECT * FROM pets WHERE categoryId = :categoryId")
    fun getPetsByCategory(categoryId: String): Flow<List<PetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPets(pets: List<PetEntity>)

    @Query("DELETE FROM pets")
    suspend fun clearPets()

    @Query("DELETE FROM pets WHERE categoryId = :categoryId")
    suspend fun deletePetsByCategoryId(categoryId: String)
}