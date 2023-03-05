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
        e?.printStackTrace()
        log.severe("An exception was caught! $e")
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e?.message ?: "Internal server error",
                )
            )
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(e: AuthenticationException): ResponseEntity<ErrorResponse> {
        e.printStackTrace()
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
        e.printStackTrace()
        log.severe("An exception was caught! $e")
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND.value())
            .body(
                ErrorResponse(
                    HttpStatus.NOT_FOUND.value(),
                    "Can't find entity by id ${e.message!!}"
                )
            )
    }

    @ExceptionHandler(ForbiddenActionException::class)
    fun handleForbiddenException(e: ForbiddenActionException): ResponseEntity<ErrorResponse> {
        e.printStackTrace()
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

    @ExceptionHandler(UsernameAlreadyExistsException::class)
    fun handleUsernameAlreadyExistsException(e: UsernameAlreadyExistsException): ResponseEntity<ErrorResponse> {
        e.printStackTrace()
        log.severe("An exception was caught! $e")
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST.value())
            .body(
                ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    "Username ${e.message} is already taken"
                )
            )
    }
}
