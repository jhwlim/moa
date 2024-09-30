package io.webapp.moa.user.infrastructure.auth

import io.kotest.matchers.shouldBe
import io.webapp.moa.SpringTestSpec
import io.webapp.moa.TestSecurityConfig
import io.webapp.moa.support.utils.getErrorResponse
import io.webapp.moa.support.utils.post
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest(TestAuthController::class)
@Import(
    TestSecurityConfig::class,
)
class AuthenticationFilterTest(
    private val mockMvc: MockMvc,
) : SpringTestSpec({

    describe("인증이 필요한 API") {

        fun requestTestAuth() = mockMvc.post(
            uri = "/auth/test",
            request = null
        )

        context("Authorization 헤더가 비어 있는 경우") {

            val actualAction = requestTestAuth()

            it("status 는 401 이어야 한다.") {
                actualAction.andExpect { status { isUnauthorized() } }
            }

            it("errorCode 는 901 이어야 한다.") {
                val actualErrorResponse = actualAction.getErrorResponse()
                actualErrorResponse.errorCode shouldBe "ERR-901"
            }

        }

    }

})
