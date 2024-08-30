package io.webapp.moa.user.presentation.dto

import io.webapp.moa.user.application.dto.CreateUserCommand
import io.webapp.moa.user.domain.model.value.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class RegisterUserRequest(
    @field:NotBlank(message = "이메일은 공백이나 빈 문자열이 아닌 값이어야 합니다.")
    @field:jakarta.validation.constraints.Email(message = "이메일 형식이 유효하지 않습니다.")
    val email: String?,

    @field:NotBlank(message = "비밀번호는 공백이나 빈 문자열이 아닌 값이어야 합니다.")
    @field:Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$",
        message = "비밀번호는 최소 8자 이상, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다."
    )
    val password: String?,

    @field:NotBlank(message = "이름은 공백이나 빈 문자열이 아닌 값이어야 합니다.")
    val name: String?,
) {

    fun toCreateUserCommand() = CreateUserCommand(
        email = Email(email!!),
        rawPassword = password!!,
        name = name!!,
    )

}
