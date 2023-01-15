package com.coursework.iGSE.utils

import com.coursework.iGSE.entity.Customer
import com.coursework.iGSE.models.UserInfo
import io.jsonwebtoken.*
import org.springframework.stereotype.Component
import java.util.*

@Component(value = "JwtComponent")
class JwtUtils {
    private val secret = "secret"
    private val expiryDuration = Date(System.currentTimeMillis() + 60 * 24 * 10000)

    fun generateJWT(user: Customer): String {
        return Jwts.builder()
            .setIssuer(user.customerId)
            .claim("customerId", user.customerId)
            .claim("role", user.type)
            .setExpiration(expiryDuration)
            .signWith(SignatureAlgorithm.HS256, secret).compact()
    }

    fun verify(token: String) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
        } catch (e: Exception) {
            throw JwtException(e.toString())
        }
    }

    fun getUserInfo(token: String): UserInfo {
        val data = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
        return UserInfo(data["customerId"].toString(), data["role"].toString())
    }

    fun verifyRole(authorization: String, role: String): Boolean {
        try {
            val res = Jwts.parser().setSigningKey(secret).parseClaimsJwt(authorization).body
            return res["role"] == role
        } catch (e: Exception) {
            throw java.lang.Exception()
        }
    }
}
