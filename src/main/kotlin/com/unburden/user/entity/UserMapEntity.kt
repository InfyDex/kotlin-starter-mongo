package com.unburden.user.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "userMap")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class UserMapEntity (
    @Id
    val id: String,

    @Field("userId")
    @JsonProperty("userId")
    val userId: String,

    @Field("userToken")
    @JsonProperty("userToken")
    val userToken: String
)