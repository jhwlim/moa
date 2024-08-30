package io.webapp.moa.user.presentation.dto

import io.webapp.moa.user.application.dto.SignInCommand
import io.webapp.moa.user.domain.model.value.Email
import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank(message = "이메일은 공백이나 빈 문자열이 아닌 값이어야 합니다.")
    val email: String?,
    @field:NotBlank(message = "비밀번호는 공백이나 빈 문자열이 아닌 값이어야 합니다.")
    val password: String?,
) {

    fun toSignInCommand() = SignInCommand(
        email = Email(email!!),
        rawPassword = password!!,
    )

}
