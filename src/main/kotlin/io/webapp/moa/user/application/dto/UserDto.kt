package io.webapp.moa.user.application.dto

import io.webapp.moa.user.domain.model.aggregate.User
import io.webapp.moa.user.domain.model.value.Email

data class UserDto(
    val email: Email,
    val name: String,
) {

    companion object {

        fun of(user: User) = with(user) {
            UserDto(
                email = email,
                name = name,
            )
        }

    }

}
