package io.webapp.moa.user.application

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.webapp.moa.support.fixture.UserFixtures.defaultCreateUserCommand
import io.webapp.moa.support.fixture.UserFixtures.defaultSavedUser
import io.webapp.moa.support.fixture.UserFixtures.defaultUserDto
import io.webapp.moa.user.application.validator.CreateUserValidator
import io.webapp.moa.user.domain.model.aggregate.User
import io.webapp.moa.user.domain.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder

class UserServiceTest : DescribeSpec({

    val userRepository: UserRepository = mockk()
    val createUserValidator: CreateUserValidator = mockk()
    val passwordEncoder: PasswordEncoder = mockk()

    val userService = UserService(
        userRepository,
        createUserValidator,
        passwordEncoder,
    )

    describe("signUp") {

        context("유효한 사용자 정보가 전달되는 경우") {

            val userCommand = defaultCreateUserCommand()
            val user = defaultSavedUser()
            val userDto = defaultUserDto()

            every { createUserValidator.validate(userCommand) } returns Unit
            every { passwordEncoder.encode(userCommand.rawPassword) } returns "encodedPassword1234"
            every { userRepository.save(any<User>()) } returns user

            val actual = userService.signUp(userCommand)

            it("등록된 사용자 정보가 반환되어야 한다.") {
                actual shouldBe userDto
            }

        }

    }

})
