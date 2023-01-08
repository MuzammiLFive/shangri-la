package com.coursework.iGSE.controller

import com.coursework.iGSE.entity.Role
import com.coursework.iGSE.models.Message
import com.coursework.iGSE.models.TaiffUpdate
import com.coursework.iGSE.service.ReadingService
import com.coursework.iGSE.service.TaiffService
import com.coursework.iGSE.utils.JwtUtils
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin")
class AdminController(
    private val jwtUtils: JwtUtils,
    private val readingService: ReadingService,
    private val taiffService: TaiffService
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

    @GetMapping("/get-taiff")
    fun getTaiff(
        @RequestHeader(value = "authorization", defaultValue = "") authToken: String,
    ): ResponseEntity<Any> {
        try {
            jwtUtils.verify(authToken)
            val res = jwtUtils.getUserInfo(authToken)
            if (res.role != Role.admin.name) {
                return ResponseEntity.status(403).body(Message("Unauthorized Access"))
            }
            return ResponseEntity.ok().body(taiffService.getTaiff())
        } catch (e: Exception) {
            return ResponseEntity.badRequest().body(Message(e.toString()))
        }
    }

    @PostMapping("/update-taiff")
    fun updateTaiff(
        @RequestHeader(value = "authorization", defaultValue = "") authToken: String,
        @RequestBody request: TaiffUpdate
    ): ResponseEntity<Any> {
        try {
            jwtUtils.verify(authToken)
            val res = jwtUtils.getUserInfo(authToken)
            if (res.role != Role.admin.name) {
                return ResponseEntity.status(403).body(Message("Unauthorized Access"))
            }

            if (request.electricityDay != null) {
                taiffService.updateTaiffRate("electricity_day", request.electricityDay)
            }
            if (request.electricityNight != null) {
                taiffService.updateTaiffRate("electricity_night", request.electricityNight)
            }
            if (request.gas != null) {
                taiffService.updateTaiffRate("gas", request.gas)
            }

            return ResponseEntity.ok().body(Message("Updated Taiff!"))
        } catch (e: Exception) {
            return ResponseEntity.badRequest().body(Message(e.toString()))
        }
    }

    @GetMapping("/statistics")
    fun getStatistics(
        @RequestHeader(value = "authorization", defaultValue = "") authToken: String,
    ): ResponseEntity<Any> {
        try {
            jwtUtils.verify(authToken)
            val res = jwtUtils.getUserInfo(authToken)
            if (res.role != Role.admin.name) {
                return ResponseEntity.status(403).body(Message("Unauthorized Access"))
            }

            return ResponseEntity.ok().body(readingService.statistics())
        } catch (e: Exception) {
            return ResponseEntity.badRequest().body(Message(e.toString()))
        }
    }
}
