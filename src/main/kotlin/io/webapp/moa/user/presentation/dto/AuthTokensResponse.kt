package io.webapp.moa.user.presentation.dto

import io.webapp.moa.user.application.auth.AccessToken
import io.webapp.moa.user.application.auth.RefreshToken
import io.webapp.moa.user.application.dto.AuthTokens

data class AuthTokensResponse(
    val accessToken: AccessToken,
    val refreshToken: RefreshToken,
) {

    companion object {

        fun from(authTokens: AuthTokens) = with(authTokens) {
            AuthTokensResponse(
                accessToken = accessToken,
                refreshToken = refreshToken,
            )
        }

    }

}
