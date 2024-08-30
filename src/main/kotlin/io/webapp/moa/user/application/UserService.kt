package io.webapp.moa.user.application

import io.webapp.moa.user.application.auth.AccessTokenProvider
import io.webapp.moa.user.application.auth.RefreshTokenProvider
import io.webapp.moa.user.application.dto.AuthTokens
import io.webapp.moa.user.application.dto.CreateUserCommand
import io.webapp.moa.user.application.dto.SignInCommand
import io.webapp.moa.user.application.dto.UserDto
import io.webapp.moa.user.application.validator.CreateUserValidator
import io.webapp.moa.user.application.validator.SignInValidator
import io.webapp.moa.user.domain.repository.UserRepository
import io.webapp.moa.user.domain.repository.findByEmailOrThrow
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val createUserValidator: CreateUserValidator,
    private val signInValidator: SignInValidator,
    private val passwordEncoder: PasswordEncoder,
    private val accessTokenProvider: AccessTokenProvider,
    private val refreshTokenProvider: RefreshTokenProvider,
) {

    @Transactional
    fun signUp(user: CreateUserCommand): UserDto {
        createUserValidator.validate(user)

        return userRepository.save(user.toEntity(passwordEncoder))
            .let { UserDto.from(it) }
    }

    @Transactional(readOnly = true)
    fun signIn(command: SignInCommand): AuthTokens {
        signInValidator.validate(command)

        return userRepository.findByEmailOrThrow(command.email)
            .let { user ->
                AuthTokens(
                    accessToken = accessTokenProvider.createAccessToken(user),
                    refreshToken = refreshTokenProvider.createRefreshToken(user),
                )
            }
    }

}
