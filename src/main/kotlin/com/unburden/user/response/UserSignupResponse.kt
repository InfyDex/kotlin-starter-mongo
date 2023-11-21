package com.unburden.user.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.unburden.user.entity.UserEntity

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class UserSignupResponse (
    @JsonProperty("userToken")
    val userToken: String,

    @JsonProperty("user")
    val user: UserEntity
)