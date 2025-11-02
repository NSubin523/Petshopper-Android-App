package com.example.petshopper.core.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.petshopper.core.domain.model.UserModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

object AuthDataStore {
    private val JWT_TOKEN_KEY = stringPreferencesKey("jwt_token")
    private val USER_DATA_KEY = stringPreferencesKey("user_data")
    private val gson = Gson()

    suspend fun saveJwt(context: Context, token: String) {
        context.dataStore.edit { preferences ->
            preferences[JWT_TOKEN_KEY] = token
        }
    }

    suspend fun getJwt(context: Context): String? {
        return context.dataStore.data.map { preferences ->
            preferences[JWT_TOKEN_KEY]
        }.first()
    }

    suspend fun saveUser(context: Context, user: UserModel) {
        context.dataStore.edit { preferences ->
            preferences[USER_DATA_KEY] = gson.toJson(user)
        }
    }

    suspend fun getUser(context: Context): UserModel? {
        val userJson = context.dataStore.data.map { preferences ->
            preferences[USER_DATA_KEY]
        }.first()

        return userJson?.let {
            try {
                gson.fromJson(it, UserModel::class.java)
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun clear(context: Context) {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}