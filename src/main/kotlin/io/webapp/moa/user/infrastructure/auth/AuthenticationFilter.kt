package io.webapp.moa.user.infrastructure.auth

import io.webapp.moa.common.utils.logger
import io.webapp.moa.user.application.auth.AccessToken
import io.webapp.moa.user.application.auth.AccessTokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class AuthenticationFilter(
    private val accessTokenProvider: AccessTokenProvider,
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
        getToken(request)?.let { accessToken ->
            try {
                accessTokenProvider.getAuthentication(accessToken)
            } catch (e: Exception) {
                log.warn(e) {}

                // TODO: 인증 에러 응답
            }
        }

        filterChain.doFilter(request, response)
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

}
