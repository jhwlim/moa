package io.webapp.moa.user.application.dto

import io.webapp.moa.user.domain.model.value.Email

data class SignInCommand(
    val email: Email,
    val rawPassword: String,
)
