package com.unburden.security.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm

object JwtHelper {
    fun generateUserToken(id: String): String {
        val entitlements = arrayOf("unburden.user")
        val jwtPayload: Map<String, Any> =
            mapOf(
                "sub" to id,
                "user" to mapOf(
                    "entitlements" to entitlements,
                )
            )

        val algorithm: Algorithm = Algorithm.HMAC256("secret")

        return JWT.create()
            .withIssuer("auth0")
            .withHeader(jwtPayload)
            .sign(algorithm)
    }
}