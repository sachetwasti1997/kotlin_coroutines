package com.example.kotlin_coroutines.exceptionHandler

import org.springframework.http.HttpStatus

class ErrorResponse(
    private val message: String?,
    private val status: HttpStatus
) {
    override fun toString(): String {
        return "{message='$message', status=$status}"
    }
}