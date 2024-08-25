package io.webapp.moa.user.domain.repository

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
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

    describe("save") {

        context("새로운 사용자가 등록되었을 때") {

            val user = User(
                email = Email("test@example.com"),
                password = EncryptedPassword("password"),
                name = "Test User"
            )

            val actual = userRepository.save(user)

            it("id는 0이 아니어야 한다.") {
                actual.id shouldNotBe 0L
            }

        }

    }

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

            val actual = userRepository.findByEmail(email)

            it("해당 이메일을 가진 사용자를 반환해야 한다.") {
                actual shouldBe user
            }

        }

        context("이메일로 사용자를 찾을 수 없을 때") {

            val email = Email("test22@example.com")

            val actual = userRepository.findByEmail(email)

            it("null 을 반환해야 한다.") {
                actual shouldBe null
            }

        }

    }

})
