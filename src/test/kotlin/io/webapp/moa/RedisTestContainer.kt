package io.webapp.moa

import io.lettuce.core.RedisClient
import io.lettuce.core.RedisURI
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.sync.RedisCommands
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.Network
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
class RedisTestContainer {

    companion object {

        const val REDIS_CONTAINER_PORT = 6379

        @Container
        val REDIS_CONTAINER = GenericContainer<Nothing>("redis:5.0.3-alpine").apply {
            withExposedPorts(REDIS_CONTAINER_PORT)
            network = Network.SHARED
            start()
        }

        fun getTestSupport(): RedisTestSupport {
            val redisClient = createRedisClient()
            val connection = redisClient.connect()
            return RedisTestSupport(
                redisCommands = connection.sync(),
                connection = connection,
                redisClient = redisClient,
            )
        }

        private fun createRedisClient(): RedisClient {
            val redisHost = REDIS_CONTAINER.host
            val redisPort = REDIS_CONTAINER.getMappedPort(REDIS_CONTAINER_PORT)
            return RedisClient.create(
                RedisURI.Builder
                    .redis(redisHost, redisPort)
                    .withDatabase(0)
                    .build()
            )
        }
    }

}

data class RedisTestSupport(
    val redisCommands: RedisCommands<String, String>,
    private val connection: StatefulRedisConnection<String, String>,
    private val redisClient: RedisClient,
) {

    fun close() {
        connection.close()
        redisClient.shutdown()
    }

}
