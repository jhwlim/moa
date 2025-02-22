package io.webapp.moa.support.fixture

import io.webapp.moa.user.application.auth.AccessToken
import io.webapp.moa.user.application.dto.*
import io.webapp.moa.user.domain.model.aggregate.User
import io.webapp.moa.user.domain.model.data.RefreshToken
import io.webapp.moa.user.domain.model.value.Email
import io.webapp.moa.user.domain.model.value.EncryptedPassword
import io.webapp.moa.user.domain.model.value.UserRole
import io.webapp.moa.user.presentation.dto.LoginRequest
import io.webapp.moa.user.presentation.dto.RefreshTokenResponse
import io.webapp.moa.user.presentation.dto.RegisterUserRequest

object UserFixtures {

    private const val DEFAULT_EMAIL_TEXT = "test@example.com"
    private const val DEFAULT_RAW_PASSWORD_TEXT = "1234aA!@"
    const val DEFAULT_ENCRYPTED_PASSWORD_TEXT = "encryptedPassword1234"
    const val DEFAULT_USER_NAME = "Test User"
    private const val DEFAULT_ACCESS_TOKEN_VALUE = "accessToken"
    private const val DEFAULT_REFRESH_TOKEN_VALUE = "refreshToken"

    fun defaultEmail() = Email(DEFAULT_EMAIL_TEXT)

    fun defaultEncryptedPassword() = EncryptedPassword(DEFAULT_ENCRYPTED_PASSWORD_TEXT)

    fun defaultNotSavedUser() = createUser(id = 0L)

    fun defaultSavedUser() = createUser(id = 1L)

    private fun createUser(
        id: Long = 0L,
        email: String = DEFAULT_EMAIL_TEXT,
        password: String = DEFAULT_ENCRYPTED_PASSWORD_TEXT,
        name: String = DEFAULT_USER_NAME,
        role: UserRole = UserRole.MEMBER,
    ) = User(
        id = id,
        email = Email(email),
        password = EncryptedPassword(password),
        name = name,
        role = role,
    )

    fun defaultCreateUserCommand() = createCreateUserCommand()

    private fun createCreateUserCommand(
        email: String = DEFAULT_EMAIL_TEXT,
        rawPassword: String = DEFAULT_RAW_PASSWORD_TEXT,
        name: String = DEFAULT_USER_NAME,
    ) = CreateUserCommand(
        email = Email(email),
        rawPassword = rawPassword,
        name = name,
    )

    fun defaultUserDto() = createUserDto()

    private fun createUserDto(
        id: Long = 1L,
        email: String = DEFAULT_EMAIL_TEXT,
        name: String = DEFAULT_USER_NAME,
    ) = UserDto(
        id = id,
        email = Email(email),
        name = name,
    )

    fun defaultSignInCommand() = createSignInCommand()

    private fun createSignInCommand(
        email: String = DEFAULT_EMAIL_TEXT,
        rawPassword: String = DEFAULT_RAW_PASSWORD_TEXT,
    ) = SignInCommand(
        email = Email(email),
        rawPassword = rawPassword,
    )

    fun defaultAccessToken() = AccessToken(DEFAULT_ACCESS_TOKEN_VALUE)

    fun defaultRefreshToken() = RefreshToken(DEFAULT_REFRESH_TOKEN_VALUE)

    fun defaultAuthTokens() = AuthTokens(
        accessToken = defaultAccessToken(),
        refreshToken = defaultRefreshTokenDto(),
    )

    private fun defaultRefreshTokenDto() = RefreshTokenDto(DEFAULT_REFRESH_TOKEN_VALUE)

    fun createRegisterUserRequest(
        email: String? = DEFAULT_EMAIL_TEXT,
        password: String? = DEFAULT_RAW_PASSWORD_TEXT,
        name: String? = DEFAULT_USER_NAME,
    ) = RegisterUserRequest(
        email = email,
        password = password,
        name = name,
    )

    fun createLoginRequest(
        email: String? = DEFAULT_EMAIL_TEXT,
        password: String? = DEFAULT_RAW_PASSWORD_TEXT,
    ) = LoginRequest(
        email = email,
        password = password,
    )

    fun defaultRefreshTokenResponse() = RefreshTokenResponse(DEFAULT_REFRESH_TOKEN_VALUE)

}
