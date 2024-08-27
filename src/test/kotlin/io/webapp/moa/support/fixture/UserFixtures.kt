package io.webapp.moa.support.fixture

import io.webapp.moa.user.application.dto.CreateUserCommand
import io.webapp.moa.user.domain.model.aggregate.User
import io.webapp.moa.user.domain.model.value.Email
import io.webapp.moa.user.domain.model.value.EncryptedPassword

object UserFixtures {

    private const val DEFAULT_EMAIL_TEXT = "test@example.com"
    private const val DEFAULT_PASSWORD_TEXT = "rawPassword1234"
    private const val DEFAULT_USER_NAME = "Test User"

    fun defaultEmail() = Email(DEFAULT_EMAIL_TEXT)

    fun defaultPassword() = EncryptedPassword(DEFAULT_PASSWORD_TEXT)

    fun defaultUserNotSaved() = createUser(id = 0L)

    fun defaultUser() = createUser(id = 1L)

    private fun createUser(
        id: Long = 0L,
        email: String = DEFAULT_EMAIL_TEXT,
        password: String = DEFAULT_PASSWORD_TEXT,
        name: String = DEFAULT_USER_NAME,
    ) = User(
        id = id,
        email = Email(email),
        password = EncryptedPassword(password),
        name = name,
    )

    fun defaultCreateUserCommand() = createCreateUserCommand()

    private fun createCreateUserCommand(
        email: String = DEFAULT_EMAIL_TEXT,
        rawPassword: String = DEFAULT_PASSWORD_TEXT,
        name: String = DEFAULT_USER_NAME,
    ) = CreateUserCommand(
        email = Email(email),
        rawPassword = rawPassword,
        name = name,
    )

}
