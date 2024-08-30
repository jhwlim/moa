package io.webapp.moa.user.presentation.dto

import io.webapp.moa.user.application.dto.UserDto
import io.webapp.moa.user.domain.model.value.Email

data class UserResponse(
    val id: Long,
    val name: String,
    val email: Email,
) {

    companion object {

        fun from(user: UserDto) = with(user) {
            UserResponse(
                id = id,
                name = name,
                email = email,
            )
        }

    }

}
