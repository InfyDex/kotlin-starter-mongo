package com.unburden.user

import com.unburden.security.jwt.JwtHelper
import com.unburden.user.boundary.UserInteractorBoundary
import com.unburden.user.boundary.UserRepositoryBoundary
import com.unburden.user.entity.UserEntity
import com.unburden.user.request.UserRequest
import com.unburden.user.response.UserSignupResponse
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserInteractor(
    private val userRepository: UserRepositoryBoundary
) : UserInteractorBoundary {
    override fun createUser(user: UserRequest): UserSignupResponse {
        val generatedUserId = UUID.randomUUID().toString()
        val userToken = JwtHelper.generateUserToken(generatedUserId)
        val createUser = userRepository.createUser(user, userToken, generatedUserId)
        return UserSignupResponse(userToken = userToken, user = createUser)
    }

    override fun profile(userToken: String): UserEntity? {
        return userRepository.profile(userToken)
    }
}