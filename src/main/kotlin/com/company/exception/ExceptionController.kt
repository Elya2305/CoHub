package com.company.exception

import com.company.exception.custom.AuthenticationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionController {

    @ExceptionHandler(Exception::class)
    fun handleInternalException(e: Exception?): ResponseEntity<ErrorResponse?>? {
        print("An exception was caught! $e")
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal exception occurred"
                )
            )
    }

    // todo
    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(e: AuthenticationException): ResponseEntity<ErrorResponse?>? {
        print("An exception was caught! $e")
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN.value())
            .body(
                ErrorResponse(
                    HttpStatus.FORBIDDEN.value(),
                    e.message!!
                )
            )
    }
}