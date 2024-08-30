package io.webapp.moa.user.presentation

import io.webapp.moa.user.application.UserService
import io.webapp.moa.user.presentation.dto.AuthTokensResponse
import io.webapp.moa.user.presentation.dto.LoginRequest
import io.webapp.moa.user.presentation.dto.RegisterUserRequest
import io.webapp.moa.user.presentation.dto.UserResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/auth")
class UserAuthController(
    private val userService: UserService,
) {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerUser(
        @RequestBody @Valid request: RegisterUserRequest,
    ): UserResponse {
        return userService.signUp(request.toCreateUserCommand())
            .let { UserResponse.from(it) }
    }

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid request: LoginRequest,
    ): AuthTokensResponse {
        return userService.signIn(request.toSignInCommand())
            .let { AuthTokensResponse.from(it) }
    }

}
