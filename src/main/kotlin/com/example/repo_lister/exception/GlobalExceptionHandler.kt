package com.example.repo_lister.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFound(e: UserNotFoundException): ResponseEntity<ErrorMessageModel> {
        val response = ErrorMessageModel(
            status = e.statusCode.value(),
            message = "User not found"
        )

        return ResponseEntity(response, e.statusCode)
    }
}