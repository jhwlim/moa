package io.webapp.moa.common.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.webapp.moa.user.application.auth.AccessTokenProvider
import io.webapp.moa.user.infrastructure.auth.AuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val accessTokenProvider: AccessTokenProvider,
    private val objectMapper: ObjectMapper,
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .authorizeHttpRequests {
                it.requestMatchers(*PERMIT_ALL_PATTERNS)
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationFilter(): AuthenticationFilter {
        return AuthenticationFilter(
            accessTokenProvider = accessTokenProvider,
            objectMapper = objectMapper,
        )
    }

    companion object {

        val PERMIT_ALL_PATTERNS = arrayOf(
            "/v1/auth/register",
            "/v1/auth/login",
        )

    }

}
