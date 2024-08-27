package io.webapp.moa.user.domain.repository

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.webapp.moa.SpringTestSpec
import io.webapp.moa.support.fixture.UserFixtures.defaultEmail
import io.webapp.moa.support.fixture.UserFixtures.defaultNotSavedUser
import io.webapp.moa.user.domain.model.value.Email
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class UserRepositoryTest(
    private val userRepository: UserRepository,
    private val testEntityManager: TestEntityManager,
) : SpringTestSpec({

    describe("save") {

        context("새로운 사용자가 등록되었을 때") {

            val user = defaultNotSavedUser()

            val actual = userRepository.save(user)

            it("id는 0이 아니어야 한다.") {
                actual.id shouldNotBe 0L
            }

        }

    }

    describe("findByEmail") {

        val user = defaultNotSavedUser()

        testEntityManager.persistAndFlush(user)

        context("이메일로 사용자를 찾을 수 있을 때") {

            val email = defaultEmail()

            val actual = userRepository.findByEmail(email)

            it("null 을 반환하지 않아야 한다.") {
                actual shouldNotBe null
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
