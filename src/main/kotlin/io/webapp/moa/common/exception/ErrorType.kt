package io.webapp.moa.common.exception

enum class ErrorType(
    private val code: Int,
    val message: String,
) {
    ;

    fun getErrorCode() = "ERR-$code"
}
