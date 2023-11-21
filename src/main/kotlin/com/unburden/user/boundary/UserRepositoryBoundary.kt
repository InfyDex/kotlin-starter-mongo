package com.unburden.user.boundary

import com.unburden.user.entity.UserEntity
import com.unburden.user.request.UserRequest

interface UserRepositoryBoundary {
    fun createUser(userRequest: UserRequest, userToken: String, userId: String): UserEntity
    fun profile(userToken: String): UserEntity?
}