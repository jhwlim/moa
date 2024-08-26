package io.webapp.moa.user.application.validator

import io.webapp.moa.common.exception.ErrorType.USER_EMAIL_ALREADY_EXISTS
import io.webapp.moa.common.exception.ValidationException
import io.webapp.moa.user.application.dto.CreateUserCommand
import io.webapp.moa.user.domain.model.value.Email
import io.webapp.moa.user.domain.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class CreateUserValidator(
    private val userRepository: UserRepository,
) {

    fun validate(command: CreateUserCommand) {
        val email = command.email
        if (emailExists(email)) {
            throw ValidationException(USER_EMAIL_ALREADY_EXISTS)
        }
    }

    private fun emailExists(email: Email): Boolean {
        return userRepository.findByEmail(email) != null
    }

}
