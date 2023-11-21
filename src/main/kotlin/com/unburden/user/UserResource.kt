package com.unburden.user

import com.unburden.user.boundary.UserInteractorBoundary
import com.unburden.user.entity.UserEntity
import com.unburden.user.request.UserRequest
import com.unburden.user.response.UserSignupResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.core.MediaType
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@RestController
@RequestMapping("/api/user")
class UserResource(private val userInteractor: UserInteractorBoundary) {
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    @Consumes(MediaType.APPLICATION_JSON)
    fun signup(@RequestBody user: UserRequest, request: HttpServletRequest): UserSignupResponse {
        user.ip = request.remoteAddr
        return userInteractor.createUser(user)
    }

    @GetMapping("/profile")
    fun profile(@RequestHeader("Authorization") bearerToken: String): UserEntity? {
        val userToken = bearerToken.split(" ")[1]
        return userInteractor.profile(userToken)
    }
}