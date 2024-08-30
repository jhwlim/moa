package io.webapp.moa.user.application.dto

import io.webapp.moa.user.domain.model.aggregate.User
import io.webapp.moa.user.domain.model.value.Email

data class UserDto(
    val id: Long,
    val email: Email,
    val name: String,
) {

    companion object {

        fun from(user: User) = with(user) {
            UserDto(
                id = id,
                email = email,
                name = name,
            )
        }

    }

}
