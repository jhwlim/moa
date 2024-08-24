package io.webapp.moa.user.domain.repository

import io.kotest.matchers.shouldBe
import io.webapp.moa.SpringTestSpec
import io.webapp.moa.user.domain.model.aggregate.User
import io.webapp.moa.user.domain.model.value.Email
import io.webapp.moa.user.domain.model.value.EncryptedPassword
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class UserRepositoryTest(
    private val userRepository: UserRepository,
    private val testEntityManager: TestEntityManager,
) : SpringTestSpec({

    describe("findByEmail") {

        val user = User(
            email = Email("test@example.com"),
            password = EncryptedPassword("password"),
            name = "Test User"
        )

        beforeEach {
            testEntityManager.persist(user)
            testEntityManager.flush()
        }

        context("이메일로 사용자를 찾을 수 있을 때") {

            val email = Email("test@example.com")

            it("해당 이메일을 가진 사용자를 반환해야 한다.") {
                val actual = userRepository.findByEmail(email)
                actual shouldBe user
            }

        }

        context("이메일로 사용자를 찾을 수 없을 때") {

            val email = Email("test22@example.com")

            it("null 을 반환해야 한다.") {
                val actual = userRepository.findByEmail(email)
                actual shouldBe null
            }

        }

    }

})
