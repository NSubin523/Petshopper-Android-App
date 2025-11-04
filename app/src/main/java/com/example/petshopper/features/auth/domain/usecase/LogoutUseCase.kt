package com.example.petshopper.features.auth.domain.usecase

import android.util.Log
import com.example.petshopper.features.auth.data.dto.LogoutRequestDto
import com.example.petshopper.features.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(request: LogoutRequestDto) {
        try {
            // Call API
            val response = authRepository.logout(request = request)

            if (response.isSuccessful && response.code() == 204) {
                Log.d("LogoutUseCase", "Logout successful for user with uuid: ${request.uuid}")
            } else {
                Log.w(
                    "LogoutUseCase",
                    "Logout returned unexpected status: ${response.code()} - ${response.message()}"
                )
            }
        } catch (e: Exception) {
            // Log error but continue with local cleanup
            Log.e("LogoutUseCase", "Logout API call failed: ${e.message}", e)
        } finally {
            // Always clear local data regardless of API response
            authRepository.clearAuthData()
        }
    }
}