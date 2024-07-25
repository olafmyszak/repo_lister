package com.example.repo_lister.exception

import feign.FeignException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {
    @ExceptionHandler(FeignException.NotFound::class)
    fun handleUserNotFoundException(ex: FeignException.NotFound): ResponseEntity<ErrorMessageModel> {
        val response = ErrorMessageModel(HttpStatus.NOT_FOUND.value(), "User not found")

        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }
}