package io.webapp.moa.user.application

import io.webapp.moa.user.application.dto.CreateUserCommand
import io.webapp.moa.user.application.dto.UserDto
import io.webapp.moa.user.application.validator.CreateUserValidator
import io.webapp.moa.user.domain.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val createUserValidator: CreateUserValidator,
    private val passwordEncoder: PasswordEncoder,
) {

    @Transactional
    fun signUp(user: CreateUserCommand): UserDto {
        createUserValidator.validate(user)

        return userRepository.save(user.toEntity(passwordEncoder))
            .let { UserDto.of(it) }
    }

}
