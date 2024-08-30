package io.webapp.moa.common.exception

open class ApplicationException(
    val errorType: ErrorType,
    override val message: String = errorType.message,
) : Exception(message) {

    fun getErrorCode() = errorType.getErrorCode()

}

class ValidationException(
    errorType: ErrorType,
    message: String = errorType.message,
) : ApplicationException(errorType, message)
