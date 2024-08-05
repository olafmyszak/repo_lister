package com.example.repo_lister.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode

class UserNotFoundException(
    val statusCode: HttpStatusCode,
    val headers: HttpHeaders
) : RuntimeException("User not found")