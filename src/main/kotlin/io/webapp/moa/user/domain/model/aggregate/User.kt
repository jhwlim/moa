package io.webapp.moa.user.domain.model.aggregate

import io.webapp.moa.user.domain.model.value.Email
import io.webapp.moa.user.domain.model.value.EncryptedPassword
import io.webapp.moa.user.domain.model.value.UserRole
import jakarta.persistence.*
import org.springframework.data.domain.AbstractAggregateRoot

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val email: Email,
    val password: EncryptedPassword,
    val name: String,
    @Enumerated(EnumType.STRING)
    val role: UserRole,
) : AbstractAggregateRoot<User>()
