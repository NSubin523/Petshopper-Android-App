package com.example.petshopper.features.auth.data.mapper

import com.example.petshopper.core.domain.model.UserModel
import com.example.petshopper.features.auth.data.dto.LoginResponseDto

object LoginMapper {
    fun toUserEntity(dto: LoginResponseDto): UserModel {
        return UserModel(
            uuid = dto.user.uuid,
            firstName = dto.user.firstName,
            lastName = dto.user.lastName,
            email = dto.user.email,
            phoneNumber = dto.user.phoneNumber,
            createdAt = dto.user.createdAt,
        )
    }
}