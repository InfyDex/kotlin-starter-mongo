package com.unburden.user

import com.unburden.exception.UnableToCreateUserException
import com.unburden.exception.UnableToGetUserProfileException
import com.unburden.user.boundary.UserRepositoryBoundary
import com.unburden.user.entity.UserEntity
import com.unburden.user.entity.UserMapEntity
import com.unburden.user.request.UserRequest
import com.unburden.logger.ApplicationLogger
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class UserRepository(private val mongoOperations: MongoOperations) : UserRepositoryBoundary {
    private val logger: ApplicationLogger = ApplicationLogger.getLogger(UserRepository::class.java)

    @Transactional
    override fun createUser(userRequest: UserRequest, userToken: String, userId: String): UserEntity {
        val userEntity = UserEntity(
            deviceId = userRequest.deviceId, deviceName = userRequest.deviceName, id = userId, ip = userRequest.ip
        )
        try {
            logger.info("trying to create user with deviceId: ${userRequest.deviceId}")
            val createdUser = mongoOperations.save(userEntity)
            val userMapEntity = UserMapEntity(
                id = UUID.randomUUID().toString(), userId = userId, userToken = userToken
            )
            mongoOperations.save(userMapEntity)
            logger.info("user created successfully with id: $userId")
            return createdUser
        } catch (ex: Exception) {
            logger.error("error creating user with exception: ${ex.message}")
            throw UnableToCreateUserException()
        }
    }

    override fun profile(userToken: String): UserEntity? {
        logger.info("trying to get user profile")
        try {
            val userMapQuery = Query().addCriteria(
                Criteria.where("userToken").`is`(userToken)
            )
            val userMapEntity = mongoOperations.findOne(userMapQuery, UserMapEntity::class.java)
            val userQuery = Query().addCriteria(
                Criteria.where("id").`is`(userMapEntity?.userId)
            )
            logger.info("user profile fetch successful for id ${userMapEntity?.userId}")
            return mongoOperations.findOne(userQuery, UserEntity::class.java)
        } catch (ex: Exception) {
            logger.error("error getting user profile with exception: ${ex.message}")
            throw UnableToGetUserProfileException()
        }
    }
}