package io.webapp.moa.user.application.validator

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import io.webapp.moa.common.exception.UnauthorizedException
import io.webapp.moa.support.fixture.UserFixtures.DEFAULT_ENCRYPTED_PASSWORD_TEXT
import io.webapp.moa.support.fixture.UserFixtures.defaultSavedUser
import io.webapp.moa.support.fixture.UserFixtures.defaultSignInCommand
import io.webapp.moa.user.domain.exception.EmailNotFoundException
import io.webapp.moa.user.domain.repository.UserRepository
import io.webapp.moa.user.domain.repository.findByEmailOrThrow
import org.springframework.security.crypto.password.PasswordEncoder

class SignInValidatorTest : DescribeSpec({

    val userRepository: UserRepository = mockk()
    val passwordEncoder: PasswordEncoder = mockk()
    val validator = SignInValidator(
        userRepository,
        passwordEncoder,
    )

    describe("validate") {

        val command = defaultSignInCommand()
        val user = defaultSavedUser()

        context("유효성 검사를 통과하는 경우") {

            every { userRepository.findByEmailOrThrow(command.email) } returns user
            every { passwordEncoder.matches(command.rawPassword, DEFAULT_ENCRYPTED_PASSWORD_TEXT) } returns true

            it("예외가 발생하지 않아야 한다.") {
                shouldNotThrowAny {
                    validator.validate(command)
                }
            }

        }

        context("이메일로 사용자를 찾을 수 없는 경우") {

            every { userRepository.findByEmailOrThrow(command.email) } throws EmailNotFoundException(command.email)

            it("EmailNotFoundException 이 발생해야 한다.") {
                shouldThrow<EmailNotFoundException> {
                    validator.validate(command)
                }
            }

        }

        context("비밀번호가 일치하지 않는 경우") {

            every { userRepository.findByEmailOrThrow(command.email) } returns user
            every { passwordEncoder.matches(command.rawPassword, DEFAULT_ENCRYPTED_PASSWORD_TEXT) } returns false

            it("UnauthorizedException 이 발생해야 하고, errorType 은 INVALID_PASSWORD 이어야 한다.") {
                shouldThrow<UnauthorizedException> {
                    validator.validate(command)
                }
            }

        }

    }

})
