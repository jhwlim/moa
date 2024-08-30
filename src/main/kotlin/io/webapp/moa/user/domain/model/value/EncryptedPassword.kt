package io.webapp.moa.user.domain.model.value

import org.springframework.security.crypto.password.PasswordEncoder

@JvmInline
value class EncryptedPassword(
    private val value: String
) {

    fun matches(rawPassword: String, passwordEncoder: PasswordEncoder): Boolean {
        return passwordEncoder.matches(rawPassword, value)
    }

}
