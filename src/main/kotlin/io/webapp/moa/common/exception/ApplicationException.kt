package io.webapp.moa.common.exception

open class ApplicationException(
    private val errorType: ErrorType,
    override val message: String? = null,
) : Exception(message ?: errorType.message) {

    fun getErrorCode() = errorType.getErrorCode()

}

class ValidationException(
    errorType: ErrorType,
    message: String? = null,
) : ApplicationException(errorType, message)
