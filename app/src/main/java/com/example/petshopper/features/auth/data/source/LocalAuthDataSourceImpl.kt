package com.example.petshopper.features.auth.data.source

import android.content.Context
import com.example.petshopper.core.data.AuthDataStore
import com.example.petshopper.core.domain.model.UserModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalAuthDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LocalAuthDataSource {

    override suspend fun saveJwt(token: String) {
        AuthDataStore.saveJwt(context, token)
    }

    override suspend fun getJwt(): String? {
        return AuthDataStore.getJwt(context)
    }

    override suspend fun saveUser(user: UserModel) {
        AuthDataStore.saveUser(context, user)
    }

    override suspend fun getUser(): UserModel? {
        return AuthDataStore.getUser(context)
    }

    override suspend fun clear() {
        AuthDataStore.clear(context)
    }

    override suspend fun isLoggedIn(): Boolean {
        val jwt = getJwt()
        return !jwt.isNullOrEmpty()
    }
}
