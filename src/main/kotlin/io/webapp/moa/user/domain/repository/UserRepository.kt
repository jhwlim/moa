package io.webapp.moa.user.domain.repository

import io.webapp.moa.user.domain.exception.EmailNotFoundException
import io.webapp.moa.user.domain.model.aggregate.User
import io.webapp.moa.user.domain.model.value.Email

interface UserRepository  {
    fun save(user: User): User
    fun findByEmail(email: Email): User?
}

fun UserRepository.findByEmailOrThrow(email: Email): User {
    return findByEmail(email) ?: throw EmailNotFoundException(email)
}
