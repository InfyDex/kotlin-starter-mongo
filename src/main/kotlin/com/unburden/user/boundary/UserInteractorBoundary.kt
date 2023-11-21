package com.unburden.user.boundary

import com.unburden.user.entity.UserEntity
import com.unburden.user.request.UserRequest
import com.unburden.user.response.UserSignupResponse

interface UserInteractorBoundary {
    fun createUser(user: UserRequest): UserSignupResponse
    fun profile(userToken: String): UserEntity?
}