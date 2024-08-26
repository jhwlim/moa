package io.webapp.moa.user.application.validator

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.webapp.moa.common.exception.ErrorType.USER_EMAIL_ALREADY_EXISTS
import io.webapp.moa.common.exception.ValidationException
import io.webapp.moa.user.application.dto.CreateUserCommand
import io.webapp.moa.user.domain.model.aggregate.User
import io.webapp.moa.user.domain.model.value.Email
import io.webapp.moa.user.domain.model.value.EncryptedPassword
import io.webapp.moa.user.domain.repository.UserRepository

class CreateUserValidatorTest : DescribeSpec({

    val userRepository: UserRepository = mockk()
    val validator = CreateUserValidator(
        userRepository,
    )

    describe("validate") {

        context("유효성 검사를 통과한 경우") {

            val email = Email("test@example.com")
            val command = CreateUserCommand(
                email = email,
                rawPassword = "rawPassword1234",
                name = "Test User"
            )

            every { userRepository.findByEmail(email) } returns null

            it("예외가 발생하지 않아야 한다.") {
                shouldNotThrowAny {
                    validator.validate(command)
                }
            }

        }

        context("이메일이 이미 존재하는 경우") {

            val email = Email("test@example.com")
            val command = CreateUserCommand(
                email = email,
                rawPassword = "rawPassword1234",
                name = "Test User"
            )

            every { userRepository.findByEmail(email) } returns User(
                email = email,
                password = EncryptedPassword("password"),
                name = "Test User"
            )

            it("ValidationException 이 발생해야 하고, errorType 은 USER_EMAIL_ALREADY_EXISTS 이어야 한다.") {
                val actual = shouldThrow<ValidationException> {
                    validator.validate(command)
                }

                actual.errorType shouldBe USER_EMAIL_ALREADY_EXISTS
            }

        }

    }

})
