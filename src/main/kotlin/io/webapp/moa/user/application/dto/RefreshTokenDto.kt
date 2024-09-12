package io.webapp.moa.user.application.dto

import io.webapp.moa.user.domain.model.data.RefreshToken

data class RefreshTokenDto(
    val value: String,
) {

    companion object {

        fun from(refreshToken: RefreshToken) = with(refreshToken) {
            RefreshTokenDto(
                value = value,
            )
        }

    }

}
