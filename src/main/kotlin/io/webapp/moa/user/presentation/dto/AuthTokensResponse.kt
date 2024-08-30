package io.webapp.moa.user.presentation.dto

import io.webapp.moa.user.application.dto.AuthTokens
import io.webapp.moa.user.domain.model.value.AccessToken
import io.webapp.moa.user.domain.model.value.RefreshToken

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
