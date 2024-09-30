package io.webapp.moa.user.presentation

import com.ninjasquad.springmockk.MockkBean
import io.kotest.data.row
import io.kotest.inspectors.forAll
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.mockk.every
import io.webapp.moa.SpringTestSpec
import io.webapp.moa.TestSecurityConfig
import io.webapp.moa.support.fixture.UserFixtures.DEFAULT_USER_NAME
import io.webapp.moa.support.fixture.UserFixtures.createLoginRequest
import io.webapp.moa.support.fixture.UserFixtures.createRegisterUserRequest
import io.webapp.moa.support.fixture.UserFixtures.defaultAccessToken
import io.webapp.moa.support.fixture.UserFixtures.defaultAuthTokens
import io.webapp.moa.support.fixture.UserFixtures.defaultEmail
import io.webapp.moa.support.fixture.UserFixtures.defaultRefreshTokenResponse
import io.webapp.moa.support.fixture.UserFixtures.defaultUserDto
import io.webapp.moa.support.utils.getErrorResponse
import io.webapp.moa.support.utils.getResponse
import io.webapp.moa.support.utils.post
import io.webapp.moa.user.application.UserService
import io.webapp.moa.user.presentation.dto.AuthTokensResponse
import io.webapp.moa.user.presentation.dto.LoginRequest
import io.webapp.moa.user.presentation.dto.RegisterUserRequest
import io.webapp.moa.user.presentation.dto.UserResponse
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest(UserAuthController::class)
@Import(
    TestSecurityConfig::class,
)
class UserAuthControllerTest(
    private val mockMvc: MockMvc,
    @MockkBean
    private val userService: UserService,
) : SpringTestSpec({

    describe("회원가입 API") {

        fun requestRegisterUser(request: RegisterUserRequest) = mockMvc.post(
            uri = "/v1/auth/register",
            request = request
        )

        context("유효한 사용자 정보를 전달하는 경우") {

            val request = createRegisterUserRequest()

            every { userService.signUp(any()) } returns defaultUserDto()

            val actualAction = requestRegisterUser(request)

            it("status 는 201 이어야 한다.") {
                actualAction.andExpect { status { isCreated() } }
            }

            it("등록된 사용자 정보가 반환되어야 한다.") {
                val actualResponse = actualAction.getResponse(UserResponse::class)
                actualResponse shouldBe UserResponse(
                    id = 1L,
                    name = DEFAULT_USER_NAME,
                    email = defaultEmail(),
                )
            }

        }

        context("유효하지 않은 사용자 정보를 전달하는 경우") {

            listOf(
                row("이메일이 비어 있는 경우", createRegisterUserRequest(email = null), "이메일은 공백이나 빈 문자열이 아닌 값이어야 합니다."),
                row("이메일이 빈 문자열인 경우", createRegisterUserRequest(email = " "), "이메일은 공백이나 빈 문자열이 아닌 값이어야 합니다."),
                row("이메일이 유효하지 않은 형식인 경우", createRegisterUserRequest(email = "invalid email"), "이메일 형식이 유효하지 않습니다."),
                row("비밀번호가 비어 있는 경우", createRegisterUserRequest(password = null), "비밀번호는 공백이나 빈 문자열이 아닌 값이어야 합니다."),
                row("비밀번호가 빈 문자열인 경우", createRegisterUserRequest(password = " "), "비밀번호는 공백이나 빈 문자열이 아닌 값이어야 합니다."),
                row("비밀번호가 8자보다 적은 경우", createRegisterUserRequest(password = "123aA!@"), "비밀번호는 최소 8자 이상, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다."),
                row("비밀번호가 숫자를 포함하지 않은 경우", createRegisterUserRequest(password = "aaaaaA!@"), "비밀번호는 최소 8자 이상, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다."),
                row("비밀번호가 대문자를 포함하지 않는 경우", createRegisterUserRequest(password = "1234aa!@"), "비밀번호는 최소 8자 이상, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다."),
                row("비밀번호가 소문자를 포함하지 않는 경우", createRegisterUserRequest(password = "1234AA!@"), "비밀번호는 최소 8자 이상, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다."),
                row("비밀번호가 특수문자를 포함하지 않는 경우", createRegisterUserRequest(password = "1234aAbB"), "비밀번호는 최소 8자 이상, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다."),
                row("이름이 비어 있는 경우", createRegisterUserRequest(name = null), "이름은 공백이나 빈 문자열이 아닌 값이어야 합니다."),
                row("이름이 빈 문자열인 경우", createRegisterUserRequest(name = " "), "이름은 공백이나 빈 문자열이 아닌 값이어야 합니다."),
            ).forAll { (testCase: String, request: RegisterUserRequest, expectedMessage: String) ->

                context(testCase) {

                    val actualAction = requestRegisterUser(request)

                    it("status 는 400 이어야 한다.") {
                        actualAction.andExpect { status { isBadRequest() } }
                    }

                    it("errorCode 는 ERR-991 이어야 하고, message 는 $expectedMessage 를 포함하고 있어야 한다.") {
                        val actualErrorResponse = actualAction.getErrorResponse()
                        actualErrorResponse.errorCode shouldBe "ERR-991"
                        actualErrorResponse.message shouldContain expectedMessage
                    }

                }

            }

        }

    }

    describe("로그인 API") {

        fun requestLogin(request: LoginRequest) = mockMvc.post(
            uri = "/v1/auth/login",
            request = request
        )

        context("유효한 자격 증명 정보를 전달하는 경우") {

            val request = createLoginRequest()

            every { userService.signIn(any()) } returns defaultAuthTokens()

            val actualAction = requestLogin(request)

            it("status 는 200 이어야 한다.") {
                actualAction.andExpect { status { isOk() } }
            }

            it("토큰 정보가 반환되어야 한다.") {
                val actualResponse = actualAction.getResponse(AuthTokensResponse::class)
                actualResponse shouldBe AuthTokensResponse(
                    accessToken = defaultAccessToken(),
                    refreshToken = defaultRefreshTokenResponse(),
                )
            }

        }

        context("유효하지 않은 자격 증명 정보를 전달하는 경우") {

            listOf(
                row("이메일이 비어 있는 경우", createLoginRequest(email = null), "이메일은 공백이나 빈 문자열이 아닌 값이어야 합니다."),
                row("이메일이 빈 문자열인 경우", createLoginRequest(email = " "), "이메일은 공백이나 빈 문자열이 아닌 값이어야 합니다."),
                row("비밀번호가 비어 있는 경우", createLoginRequest(password = null), "비밀번호는 공백이나 빈 문자열이 아닌 값이어야 합니다."),
                row("비밀번호가 빈 문자열인 경우", createLoginRequest(password = " "), "비밀번호는 공백이나 빈 문자열이 아닌 값이어야 합니다."),
            ).forAll { (testCase: String, request: LoginRequest, expectedMessage: String) ->

                context(testCase) {

                    val actualAction = requestLogin(request)

                    it("status 는 400 이어야 한다.") {
                        actualAction.andExpect { status { isBadRequest() } }
                    }

                    it("errorCode 는 ERR-991 이어야 하고, message 는 $expectedMessage 를 포함하고 있어야 한다.") {
                        val actualErrorResponse = actualAction.getErrorResponse()
                        actualErrorResponse.errorCode shouldBe "ERR-991"
                        actualErrorResponse.message shouldContain expectedMessage
                    }

                }

            }

        }

    }

})
