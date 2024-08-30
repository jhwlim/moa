package io.webapp.moa.common.exception

enum class ErrorType(
    private val code: Int,
    val message: String,
) {
    USER_EMAIL_ALREADY_EXISTS(101, "이미 등록된 이메일 입니다."),

    INVALID_REQUEST(991, "잘못된 요청입니다."),
    UNKNOWN(999, "알 수 없는 에러가 발생하였습니다."),
    ;

    fun getErrorCode() = "ERR-$code"
}
