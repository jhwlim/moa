package io.webapp.moa.user.application.dto

import io.webapp.moa.user.domain.model.aggregate.User
import io.webapp.moa.user.domain.model.value.Email
import io.webapp.moa.user.domain.model.value.EncryptedPassword
import io.webapp.moa.user.domain.model.value.UserRole.MEMBER
import org.springframework.security.crypto.password.PasswordEncoder

data class CreateUserCommand(
    val email: Email,
    val rawPassword: String,
    val name: String,
) {

    fun toEntity(passwordEncoder: PasswordEncoder) = User(
        email = email,
        password = EncryptedPassword(passwordEncoder.encode(rawPassword)),
        name = name,
        role = MEMBER,
    )

}
