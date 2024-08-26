package io.webapp.moa.common.exception

enum class ErrorType(
    private val code: Int,
    val message: String,
) {
    USER_EMAIL_ALREADY_EXISTS(101, "이미 등록된 이메일 입니다."),
    ;

    fun getErrorCode() = "ERR-$code"
}
