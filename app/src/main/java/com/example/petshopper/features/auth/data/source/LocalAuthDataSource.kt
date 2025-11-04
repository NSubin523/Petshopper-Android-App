package com.example.petshopper.features.auth.data.source

import com.example.petshopper.core.domain.model.UserModel

interface LocalAuthDataSource {
    suspend fun saveJwt(token: String)
    suspend fun getJwt(): String?
    suspend fun saveUser(user: UserModel)
    suspend fun getUser(): UserModel?
    suspend fun clear()
    suspend fun isLoggedIn(): Boolean
}
