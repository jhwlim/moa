package io.webapp.moa.common.utils

import mu.KLogger
import mu.KotlinLogging


fun <T : Any> T.logger(): KLogger = KotlinLogging.logger(this::class.java.simpleName)
