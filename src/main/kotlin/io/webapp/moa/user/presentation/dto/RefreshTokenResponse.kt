package io.webapp.moa.user.presentation.dto

import io.webapp.moa.user.application.dto.RefreshTokenDto

@JvmInline
value class RefreshTokenResponse(
    val value: String
) {

    companion object {

        fun from(refreshToken: RefreshTokenDto) = with(refreshToken) {
            RefreshTokenResponse(
                value = value,
            )
        }

    }

}
