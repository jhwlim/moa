package io.webapp.moa.common.exception

open class BaseException(
    open val errorType: ErrorType,
    override val message: String?,
) : Exception(message ?: errorType.message) {

    fun getErrorCode() = errorType.getErrorCode()

}
