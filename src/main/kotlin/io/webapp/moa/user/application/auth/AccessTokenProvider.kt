package io.webapp.moa.user.application.auth

import io.webapp.moa.user.domain.model.aggregate.User
import io.webapp.moa.user.domain.model.value.AccessToken

interface AccessTokenProvider {
    fun createAccessToken(user: User): AccessToken
}
