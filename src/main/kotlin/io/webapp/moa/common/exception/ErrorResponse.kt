package io.webapp.moa.common.exception

data class ErrorResponse(
    val errorCode: String,
    val message: String,
) {

    companion object {

        fun from(exception: ApplicationException): ErrorResponse {
            return of(
                errorType = exception.errorType,
                message = exception.message,
            )
        }

        fun of(errorType: ErrorType, message: String?) = ErrorResponse(
            errorCode = errorType.getErrorCode(),
            message = message ?: errorType.message,
        )

    }

}
