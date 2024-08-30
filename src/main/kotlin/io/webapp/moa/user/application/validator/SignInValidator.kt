package io.webapp.moa.user.application.validator

import io.webapp.moa.common.exception.ErrorType.INVALID_PASSWORD
import io.webapp.moa.common.exception.UnauthorizedException
import io.webapp.moa.user.application.dto.SignInCommand
import io.webapp.moa.user.domain.model.value.EncryptedPassword
import io.webapp.moa.user.domain.repository.UserRepository
import io.webapp.moa.user.domain.repository.findByEmailOrThrow
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class SignInValidator(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    fun validate(command: SignInCommand) {
        val (email, rawPassword) = command

        val foundUser = userRepository.findByEmailOrThrow(email)
        validatePasswordMatches(rawPassword, foundUser.password)
    }

    private fun validatePasswordMatches(rawPassword: String, userPassword: EncryptedPassword) {
        if (!userPassword.matches(rawPassword, passwordEncoder)) {
            throw UnauthorizedException(INVALID_PASSWORD)
        }
    }

}
