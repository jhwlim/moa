package io.webapp.moa.common.exception

open class EntityNotFoundException(
    errorType: ErrorType,
    override val message: String?,
) : BaseException(errorType, message)
