package io.webapp.moa.common.exception

enum class ErrorType(
    private val code: Int,
    val message: String,
) {
    USER_NOT_FOUND(101, "사용자를 찾을 수 없습니다."),
    USER_EMAIL_ALREADY_EXISTS(111, "이미 등록된 이메일 입니다."),
    INVALID_PASSWORD(121, "비밀번호가 일치하지 않습니다."),

    EMPTY_TOKEN(901, "토큰이 비어있습니다."),
    INVALID_TOKEN(902, "유효하지 않은 인증 토큰입니다."),

    INVALID_REQUEST(991, "잘못된 요청입니다."),
    UNKNOWN(999, "알 수 없는 에러가 발생하였습니다."),
    ;

    fun getErrorCode() = "ERR-$code"
}
