package io.webapp.moa.user.application

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.webapp.moa.support.fixture.UserFixtures.defaultAccessToken
import io.webapp.moa.support.fixture.UserFixtures.defaultAuthTokens
import io.webapp.moa.support.fixture.UserFixtures.defaultCreateUserCommand
import io.webapp.moa.support.fixture.UserFixtures.defaultRefreshToken
import io.webapp.moa.support.fixture.UserFixtures.defaultSavedUser
import io.webapp.moa.support.fixture.UserFixtures.defaultSignInCommand
import io.webapp.moa.support.fixture.UserFixtures.defaultUserDto
import io.webapp.moa.user.application.auth.AccessTokenProvider
import io.webapp.moa.user.application.auth.RefreshTokenProvider
import io.webapp.moa.user.application.validator.CreateUserValidator
import io.webapp.moa.user.application.validator.SignInValidator
import io.webapp.moa.user.domain.model.aggregate.User
import io.webapp.moa.user.domain.repository.UserRepository
import io.webapp.moa.user.domain.repository.findByEmailOrThrow
import org.springframework.security.crypto.password.PasswordEncoder

class UserServiceTest : DescribeSpec({

    val userRepository: UserRepository = mockk()
    val createUserValidator: CreateUserValidator = mockk()
    val signInValidator: SignInValidator = mockk()
    val passwordEncoder: PasswordEncoder = mockk()
    val accessTokenProvider: AccessTokenProvider = mockk()
    val refreshTokenProvider: RefreshTokenProvider = mockk()

    val userService = UserService(
        userRepository,
        createUserValidator,
        signInValidator,
        passwordEncoder,
        accessTokenProvider,
        refreshTokenProvider,
    )

    describe("signUp") {

        context("유효한 사용자 정보가 전달되는 경우") {

            val userCommand = defaultCreateUserCommand()
            val user = defaultSavedUser()

            every { createUserValidator.validate(userCommand) } returns Unit
            every { passwordEncoder.encode(userCommand.rawPassword) } returns "encodedPassword1234"
            every { userRepository.save(any<User>()) } returns user

            val actual = userService.signUp(userCommand)

            it("등록된 사용자 정보가 반환되어야 한다.") {
                actual shouldBe defaultUserDto()
            }

        }

    }

    describe("signIn") {

        val command = defaultSignInCommand()
        val user = defaultSavedUser()

        context("유효한 자격 증명 정보를 전달하는 경우") {

            every { signInValidator.validate(command) } returns Unit
            every { userRepository.findByEmailOrThrow(command.email) } returns user
            every { accessTokenProvider.createAccessToken(user) } returns defaultAccessToken()
            every { refreshTokenProvider.createRefreshToken(user) } returns defaultRefreshToken()

            val actual = userService.signIn(command)

            it("토큰 정보를 반환해야 한다.") {
                actual shouldBe defaultAuthTokens()
            }

        }

    }

})
