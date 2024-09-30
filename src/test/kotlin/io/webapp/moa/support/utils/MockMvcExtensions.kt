package io.webapp.moa.support.utils

import io.webapp.moa.common.exception.ErrorResponse
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.post
import java.nio.charset.StandardCharsets.UTF_8
import kotlin.reflect.KClass

private val objectMapper = objectMapperWithBasicModule()

fun MockMvc.post(uri: String, request: Any?): ResultActionsDsl {
    return this.post(uri) {
        contentType = MediaType.APPLICATION_JSON
        content = objectMapper.writeValueAsString(request)
    }.andDo { print() }
}

fun ResultActionsDsl.getErrorResponse(): ErrorResponse = getResponse(ErrorResponse::class)

fun <T : Any> ResultActionsDsl.getResponse(type: KClass<T>): T {
    val mvcResult = this.andReturn()
    return mvcResult.getResponse(type)
}

private fun <T : Any> MvcResult.getResponse(type: KClass<T>): T {
    val encodedContent = this.response.getContentAsString(UTF_8)
    return objectMapper.readValue(encodedContent, type.java)
}
