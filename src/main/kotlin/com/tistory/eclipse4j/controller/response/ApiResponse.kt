package com.tistory.eclipse4j.controller.response

import java.time.LocalDateTime
import java.time.ZoneId

data class ApiResponse<T>(
    val content: T,
    val time: Long = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
) {

    companion object {
        fun <T> ok(content: T): ApiResponse<T> {
            return ApiResponse(
                content = content
            )
        }
    }
}