package com.unburden.exception

import com.unburden.logger.ApplicationLogger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

@ControllerAdvice
@RestController
class GlobalExceptionHandler {
    private val logger: ApplicationLogger = ApplicationLogger.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(UnableToCreateUserException::class)
    fun handleUnableToCreateUserException(ex: UnableToCreateUserException): ResponseEntity<ErrorResponse> {
        logger.error("$ex")
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(
                ErrorResponse(
                    status = HttpStatus.CONFLICT.value(),
                    message = "Unable to create user"
                )
            )
    }

    @ExceptionHandler(UnableToGetUserProfileException::class)
    fun handleUnableToGetUserProfileException(ex: UnableToGetUserProfileException): ResponseEntity<ErrorResponse> {
        logger.error("$ex")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(
                ErrorResponse(
                    status = HttpStatus.BAD_REQUEST.value(),
                    message = "Unable to get user profile"
                )
            )
    }
}