package io.webapp.moa.user.application.dto

import io.webapp.moa.user.domain.model.value.AccessToken
import io.webapp.moa.user.domain.model.value.RefreshToken

data class AuthTokens(
    val accessToken: AccessToken,
    val refreshToken: RefreshToken,
)
