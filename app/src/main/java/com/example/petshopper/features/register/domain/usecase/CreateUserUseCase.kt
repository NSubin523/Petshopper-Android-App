package com.example.petshopper.features.register.domain.usecase

import com.example.petshopper.features.register.data.dto.CreateUserRequestDto
import com.example.petshopper.features.register.data.repository.CreateUserRepository
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val repo: CreateUserRepository
){
    suspend operator fun invoke(request: CreateUserRequestDto) = repo.createUser(request)
}