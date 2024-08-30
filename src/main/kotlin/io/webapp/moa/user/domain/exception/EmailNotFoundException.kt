package io.webapp.moa.user.domain.exception

import io.webapp.moa.common.exception.EntityNotFoundException
import io.webapp.moa.common.exception.ErrorType
import io.webapp.moa.user.domain.model.value.Email

class EmailNotFoundException(
    val email: Email,
) : EntityNotFoundException(
    errorType = ErrorType.USER_NOT_FOUND,
    message = "사용자를 찾을 수 없습니다. (email=$email)"
)
