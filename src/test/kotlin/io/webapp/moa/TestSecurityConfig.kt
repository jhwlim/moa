package io.webapp.moa

import io.webapp.moa.common.config.AuthJwtProperties
import io.webapp.moa.common.config.SecurityConfig
import io.webapp.moa.user.infrastructure.auth.JwtAccessTokenProvider
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Import

@TestConfiguration
@EnableConfigurationProperties(
    AuthJwtProperties::class,
)
@Import(
    SecurityConfig::class,
    JwtAccessTokenProvider::class,
)
class TestSecurityConfig
