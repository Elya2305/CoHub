package com.company.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.logging.Logger

@RestControllerAdvice
class ExceptionController {
    val log = Logger.getLogger(this.javaClass.name)

    @ExceptionHandler(Exception::class)
    fun handleInternalException(e: Exception?): ResponseEntity<ErrorResponse> {
        log.severe("An exception was caught! $e")
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Internal exception occurred"
                )
            )
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(e: AuthenticationException): ResponseEntity<ErrorResponse> {
        log.severe("An exception was caught! $e")
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN.value())
            .body(
                ErrorResponse(
                    HttpStatus.FORBIDDEN.value(),
                    e.message!!
                )
            )
    }

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(e: EntityNotFoundException): ResponseEntity<ErrorResponse> {
        log.severe("An exception was caught! $e")
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND.value())
            .body(
                ErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    e.message!!
                )
            )
    }
}
