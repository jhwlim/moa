package io.webapp.moa.user.application.dto

import io.webapp.moa.user.application.auth.AccessToken

data class AuthTokens(
    val accessToken: AccessToken,
    val refreshToken: RefreshTokenDto,
)
