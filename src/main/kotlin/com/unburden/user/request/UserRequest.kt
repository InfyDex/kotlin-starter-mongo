package com.unburden.user.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class UserRequest(
    @JsonProperty("deviceName")
    val deviceName: String,

    @JsonProperty("deviceId")
    val deviceId: String,

    var ip: String
)
