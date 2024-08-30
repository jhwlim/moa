package io.webapp.moa.common.exception

open class ApplicationException(
    override val errorType: ErrorType,
    override val message: String?,
) : BaseException(errorType, message)

class ValidationException(
    errorType: ErrorType,
    message: String? = null,
) : ApplicationException(errorType, message)

class UnauthorizedException(
    errorType: ErrorType,
    message: String? = null,
) : ApplicationException(errorType, message)
