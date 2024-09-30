package io.webapp.moa.user.infrastructure.auth

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestAuthController {

    @PostMapping("/auth/test")
    fun testAuth() { /* empty */ }

}
