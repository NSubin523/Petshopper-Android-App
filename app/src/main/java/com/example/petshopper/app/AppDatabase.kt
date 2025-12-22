package com.example.petshopper.app

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.petshopper.features.bottomnavigation.home.data.local.dao.CategoryDao
import com.example.petshopper.features.bottomnavigation.home.data.local.dao.InventoryDao
import com.example.petshopper.features.bottomnavigation.home.data.local.entity.CategoryEntity
import com.example.petshopper.features.bottomnavigation.home.data.local.entity.PetEntity

@Database(
    entities = [CategoryEntity::class, PetEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun inventoryDao(): InventoryDao
}