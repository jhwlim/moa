package io.webapp.moa.common.utils

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime
import java.util.*

class ExtensionTest : DescribeSpec({

    fun createDate(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int): Date {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month - 1)
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, second)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.time
    }

    describe("LocalDateTime.toDate()") {

        context("LocalDateTime 2024-01-01 00:00:00 을 변환하면") {

            val localDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0)

            val actual = localDateTime.toDate()
            context("Date 2024-01-01 00:00:00 으로 변환되어야 한다.") {
                actual shouldBe createDate(2024, 1, 1, 0, 0, 0)
            }

        }

    }

})
