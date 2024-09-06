package io.webapp.moa.user.infrastructure.auth

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.webapp.moa.common.config.AuthJwtProperties
import io.webapp.moa.support.fixture.UserFixtures
import io.webapp.moa.support.utils.toDateTime
import io.webapp.moa.user.application.auth.AccessToken

class JwtAccessTokenProviderTest : DescribeSpec({

    val authJwtProperties = AuthJwtProperties(
        secretKey = "Pf5i1wxDznpNjQMWGm6ODE3oA8uHLftHdjpvjk2vqSI=",
        issuer = "test",
        ttl = 60
    )

    val tokenProvider = JwtAccessTokenProvider(
        authJwtProperties,
    )

    describe("createAccessToken") {

        val user = UserFixtures.defaultSavedUser()
        val createdAt = toDateTime("2024-01-01 00:00:00")

        context("사용자 정보와 생성 일시 정보가 주어지면") {

            val actual = tokenProvider.createAccessToken(user, createdAt)

            it("해당 정보를 가진 토큰이 생성되어야 한다.") {
                val expectedValue = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ0ZXN0Iiwic3ViIjoiMSIsInJvbGUiOiJNRU1CRVIiLCJpYXQiOjE3MDQwMzQ4MDAsImV4cCI6MTcwNDAzNDg2MH0.KM8yf4TBDcq6zinTZq1ZzIgX_iBFFk0q3-P6efWHnKU"
                actual shouldBe AccessToken(expectedValue)
            }

        }

    }


})
