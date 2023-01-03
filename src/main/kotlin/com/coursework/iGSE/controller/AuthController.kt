package com.coursework.iGSE.controller

import com.coursework.iGSE.entity.Customer
import com.coursework.iGSE.entity.Role
import com.coursework.iGSE.models.LoginRequest
import com.coursework.iGSE.models.LoginResponse
import com.coursework.iGSE.models.Message
import com.coursework.iGSE.models.RegisterRequest
import com.coursework.iGSE.service.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.MessageDigest
import java.util.*

@RestController
@RequestMapping("/api")
class AuthController(private val userService: UserService) {

    @GetMapping("/hello")
    fun hello(): String {
        return "hello"
    }

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<Customer> {
        val user =
            Customer(
                request.email,
                getSHA256(request.password),
                request.address,
                request.propertyType.name,
                request.bedroomNum,
                200.0,
                Role.customer.name
            )
        return ResponseEntity.ok(userService.save(user))
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest, response: HttpServletResponse): ResponseEntity<Any> {
        val user = userService.loadUserByUsername(request.email)
            ?: return ResponseEntity.badRequest().body(Message("Invalid username or password"))
        if (!comparePassword(user, request.password)) {
            return ResponseEntity.badRequest().body(Message("Invalid username or password"))
        }

        val issuer = user.customerId
        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 1000))
            .signWith(SignatureAlgorithm.HS512, "secret").compact()

        return ResponseEntity.ok(LoginResponse(user.customerId, user.type, jwt))
    }

//    @GetMapping("/user")
//    fun user(@CookieValue("jwt") jwt: String?): ResponseEntity<Any> {
//        try {
//            if (jwt == null)
//                return ResponseEntity.status(401).body(Message("Unauthenticated"))
//            val body = Jwts.parser().setSigningKey("secret").parseClaimsJws(jwt).body
//            return ResponseEntity.ok(body)
//        } catch (e: Exception) {
//            return ResponseEntity.status(401).body(Message("Unauthenticated"))
//        }
//    }

    @PostMapping("/logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Any> {
        val cookie = Cookie("jwt", "")
        cookie.maxAge = 0
        response.addCookie(cookie)
        return ResponseEntity.ok(Message("success"))
    }

    // SHA 256
    fun comparePassword(user: Customer, password: String): Boolean {
        return getSHA256(password) == user.passwordHash
    }

    fun getSHA256(data: String): String {
        val result = ""
        try {
            val digest: MessageDigest = MessageDigest.getInstance("SHA-256")
            val hash: ByteArray = digest.digest(data.toByteArray(charset("UTF-8")))
            return bytesToHex2(hash) // make it printable
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return result
    }

    private fun bytesToHex2(hash: ByteArray): String {
        // return DatatypeConverter.printHexBinary(hash);
        val builder = StringBuilder()
        for (b in hash) {
            builder.append(String.format("%02x", b))
        }
        return builder.toString()
    }
}
