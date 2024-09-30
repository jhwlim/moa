package io.webapp.moa.user.infrastructure.auth

import com.fasterxml.jackson.databind.ObjectMapper
import io.webapp.moa.common.config.SecurityConfig.Companion.PERMIT_ALL_PATTERNS
import io.webapp.moa.common.exception.ErrorResponse
import io.webapp.moa.common.exception.ErrorType
import io.webapp.moa.common.utils.logger
import io.webapp.moa.user.application.auth.AccessToken
import io.webapp.moa.user.application.auth.AccessTokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class AuthenticationFilter(
    private val accessTokenProvider: AccessTokenProvider,
    private val objectMapper: ObjectMapper,
) : OncePerRequestFilter() {

    companion object {
        private const val AUTH_HEADER = "Authorization"
        private const val TOKEN_PREFIX = "Bearer "

        private val log = logger()
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (isAuthenticationRequired(request)) {
            getToken(request)?.let { accessToken ->
                try {
                    accessTokenProvider.getAuthentication(accessToken)

                    filterChain.doFilter(request, response)
                } catch (e: Exception) {
                    log.warn(e) {}
                    responseError(response, ErrorType.INVALID_TOKEN, e.message)
                }
            } ?: responseError(response, ErrorType.EMPTY_TOKEN)

            return
        }

        filterChain.doFilter(request, response)
    }

    private fun isAuthenticationRequired(request: HttpServletRequest): Boolean {
        return request.requestURI !in PERMIT_ALL_PATTERNS
    }

    private fun getToken(request: HttpServletRequest): AccessToken? {
        return request.getHeader(AUTH_HEADER)
            ?.let {
                if (it.startsWith(TOKEN_PREFIX))
                    AccessToken(it.substring(TOKEN_PREFIX.length))
                else
                    null
            }
    }

    private fun responseError(
        response: HttpServletResponse,
        errorType: ErrorType,
        message: String? = null,
    ) {
        response.apply {
            status = HttpStatus.UNAUTHORIZED.value()
            contentType = APPLICATION_JSON.toString()
            writer.write(objectMapper.writeValueAsString(
                    ErrorResponse.of(
                        errorType = errorType,
                        message = message ?: errorType.message,
                    )
                )
            )
        }
    }

}
