package io.webapp.moa.user.domain.repository

import io.webapp.moa.user.domain.model.aggregate.User
import io.webapp.moa.user.domain.model.value.Email

interface UserRepository  {
    fun findByEmail(email: Email): User?
}
