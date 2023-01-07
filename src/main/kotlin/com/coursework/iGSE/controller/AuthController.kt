package com.coursework.iGSE.controller

import com.coursework.iGSE.entity.Customer
import com.coursework.iGSE.entity.Role
import com.coursework.iGSE.models.*
import com.coursework.iGSE.service.UserService
import com.coursework.iGSE.service.VoucherService
import com.coursework.iGSE.utils.JwtUtils
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.MessageDigest
import java.util.*
import java.util.regex.Pattern

@RestController
@RequestMapping("/api")
class AuthController(
    private val userService: UserService,
    private val voucherService: VoucherService,
    private val jwtUtils: JwtUtils
) {

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<Any> {
        if (!isEmailValid(request.email)) {
            return ResponseEntity.badRequest().body(Message("Invalid email"))
        }
        if (userService.loadUserByUsername(request.email) != null) {
            return ResponseEntity.badRequest().body(Message("Email already registered"))
        }
        if (request.password1 != request.password2) {
            return ResponseEntity.badRequest().body(Message("Passwords do not match"))
        }
        if (!voucherService.isValidVoucher(request.voucher)) {
            return ResponseEntity.badRequest().body(Message("Invalid voucher"))
        }

        val user = Customer(
            request.email,
            getSHA256(request.password1),
            request.address,
            request.propertyType.name,
            request.bedroomNum,
            200.0,
            Role.customer.name
        )
        val newUser = userService.save(user)

        return ResponseEntity.ok(newUser)
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest, response: HttpServletResponse): ResponseEntity<Any> {
        val user = userService.loadUserByUsername(request.email) ?: return ResponseEntity.badRequest()
            .body(Message("Invalid username or password"))
        if (!comparePassword(user, request.password)) {
            return ResponseEntity.badRequest().body(Message("Invalid username or password"))
        }

        return ResponseEntity.ok(LoginResponse(user.customerId, user.type, jwtUtils.generateJWT(user)))
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

    private fun isEmailValid(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }
}
