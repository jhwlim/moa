package io.webapp.moa.user.infrastructure.persistance

import io.webapp.moa.user.domain.model.aggregate.User
import io.webapp.moa.user.domain.model.value.Email
import io.webapp.moa.user.domain.repository.UserRepository
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : UserRepository, JpaRepository<User, Long> {
    override fun save(user: User): User
    override fun findByEmail(email: Email): User?
}
