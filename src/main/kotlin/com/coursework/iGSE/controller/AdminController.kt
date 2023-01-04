package com.coursework.iGSE.controller

import com.coursework.iGSE.entity.Role
import com.coursework.iGSE.models.Message
import com.coursework.iGSE.service.ReadingService
import com.coursework.iGSE.utils.JwtUtils
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminController(
    private val jwtUtils: JwtUtils,
    private val readingService: ReadingService
) {

    @GetMapping("/readings")
    fun getAllReadings(
        @RequestHeader(value = "authorization", defaultValue = "") authToken: String
    ): ResponseEntity<Any> {
        try {
            jwtUtils.verify(authToken)
            val res = jwtUtils.getUserInfo(authToken)
            if (res.role != Role.admin.name) {
                return ResponseEntity.status(403).body(Message("Unauthorized Access"))
            }
            return ResponseEntity.ok().body(readingService.getAllReadings())
        } catch (e: Exception) {
            return ResponseEntity.badRequest().body(Message(e.toString()))
        }
    }
}
