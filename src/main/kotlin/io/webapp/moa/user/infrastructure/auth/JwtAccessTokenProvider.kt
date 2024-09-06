package io.webapp.moa.user.infrastructure.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.webapp.moa.common.config.AuthJwtProperties
import io.webapp.moa.common.utils.toDate
import io.webapp.moa.user.application.auth.AccessToken
import io.webapp.moa.user.application.auth.AccessTokenProvider
import io.webapp.moa.user.domain.model.aggregate.User
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class JwtAccessTokenProvider(
    private val authJwtProperties: AuthJwtProperties,
) : AccessTokenProvider {

    companion object {

        const val CLAIM_ROLE = "role"

    }

    private val algorithm = Algorithm.HMAC256(authJwtProperties.secretKey)

    override fun createAccessToken(user: User, createdAt: LocalDateTime): AccessToken {
        val expiresAt = createdAt.plusSeconds(authJwtProperties.ttl)

        return JWT.create()
            .withIssuer(authJwtProperties.issuer)
            .withSubject(user.id.toString())
            .withClaim(CLAIM_ROLE, user.role.name)
            .withIssuedAt(createdAt.toDate())
            .withExpiresAt(expiresAt.toDate())
            .sign(algorithm)
            .let { AccessToken(it) }
    }

}
