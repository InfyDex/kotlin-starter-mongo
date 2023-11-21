package com.unburden.user.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "user")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class UserEntity (
    @Id
    val id: String,

    @Field("deviceName")
    @JsonProperty("deviceName")
    val deviceName: String?,

    @Field("deviceId")
    @JsonProperty("deviceId")
    val deviceId: String?,

    @Field("ip")
    @JsonProperty("ip")
    val ip: String? = null
)