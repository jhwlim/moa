package io.webapp.moa.common.exception

import io.webapp.moa.common.exception.ErrorType.INVALID_REQUEST
import io.webapp.moa.common.exception.ErrorType.UNKNOWN
import io.webapp.moa.common.utils.logger
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    private val log = logger()

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorMessages = ex.bindingResult.fieldErrors.map {
            it.defaultMessage
        }

        return responseEntityOf(BAD_REQUEST) {
            ErrorResponse.of(
                errorType = INVALID_REQUEST,
                message = errorMessages.joinToString(" | ")
            )
        }
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorizedException(exception: UnauthorizedException): ResponseEntity<ErrorResponse> {
        return responseEntityOf(UNAUTHORIZED) {
            ErrorResponse.from(exception)
        }
    }

    @ExceptionHandler(ApplicationException::class)
    fun handleApplicationException(exception: ApplicationException): ResponseEntity<ErrorResponse> {
        return responseEntityOf(BAD_REQUEST) {
            ErrorResponse.from(exception)
        }
    }

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ResponseEntity<ErrorResponse> {
        log.error(exception) {}
        return responseEntityOf(INTERNAL_SERVER_ERROR) {
            ErrorResponse.of(
                errorType = UNKNOWN,
                message = exception.message,
            )
        }
    }

    private fun responseEntityOf(
        httpStatus: HttpStatus,
        body: () -> ErrorResponse
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(httpStatus)
            .body(body())
    }

}
