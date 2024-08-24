package io.webapp.moa

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode

open class SpringTestSpec(
    body: DescribeSpec.() -> Unit
) : DescribeSpec(body) {

    override fun extensions() = listOf(SpringTestExtension(SpringTestLifecycleMode.Root))

}
