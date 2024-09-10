package io.webapp.moa.user.infrastructure.cache

import io.kotest.matchers.shouldBe
import io.webapp.moa.RedisTestConfig
import io.webapp.moa.RedisTestContainer
import io.webapp.moa.RedisTestContainer.Companion.REDIS_CONTAINER
import io.webapp.moa.SpringTestSpec
import io.webapp.moa.common.config.RedisProperties
import io.webapp.moa.common.config.RedisTemplateConfig
import io.webapp.moa.support.fixture.UserFixtures.defaultRefreshToken
import io.webapp.moa.user.domain.model.data.RefreshToken
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.context.annotation.Import
import org.springframework.data.redis.core.RedisTemplate
import org.testcontainers.junit.jupiter.EnabledIfDockerAvailable

@EnabledIfDockerAvailable
@DataRedisTest
@Import(
    RedisTestConfig::class,
    RedisTemplateConfig::class,
    RedisProperties::class,
)
class RefreshTokenRedisRepositoryTest(
    private val redisTemplate: RedisTemplate<String, RefreshToken>,
    private val redisProperties: RedisProperties,
) : SpringTestSpec({

    val testSupport = RedisTestContainer.getTestSupport()
    val redisCommands = testSupport.redisCommands

    beforeSpec {
        REDIS_CONTAINER.start()
    }

    val redisRepository = RefreshTokenRedisRepository(
        redisTemplate,
        redisProperties,
    )

    describe("save") {

        val userId = 1L
        val refreshToken = defaultRefreshToken()

        context("userId 와 RefreshToken 을 저장하면") {

            redisRepository.save(userId, refreshToken)

            val redisKey = "RT_V1:1"

            it("저장한 키로 조회할 수 있어야 한다.") {
                val actual = redisCommands.get(redisKey)
                actual shouldBe "{\"value\":\"refreshToken\"}"
            }

            it("ttl 은 86400 초 여야 한다.") {
                val actual = redisCommands.ttl(redisKey)
                actual shouldBe 86400
            }

        }

    }

    describe("find") {

        context("userId에 저장된 값이 있으면") {

            val userId = 2L

            redisCommands.set("RT_V1:2", "{\"value\":\"refreshToken2\"}")

            it("저장된 값에 맵핑된 객체를 반환해야 한다.") {
                val actual = redisRepository.find(userId)
                actual shouldBe RefreshToken("refreshToken2")
            }

        }

        context("userId에 저장된 값이 없으면") {

            val userId = 3L

            it("null 을 반환해야 한다.") {
                val actual = redisRepository.find(userId)
                actual shouldBe null
            }

        }

    }

    afterSpec {
        testSupport.close()
        REDIS_CONTAINER.stop()
    }

})
