package com.coursework.iGSE.controller

import com.coursework.iGSE.models.Message
import com.coursework.iGSE.models.NewReading
import com.coursework.iGSE.models.toCustomerDetails
import com.coursework.iGSE.service.ReadingService
import com.coursework.iGSE.service.UserService
import com.coursework.iGSE.service.VoucherService
import com.coursework.iGSE.utils.JwtUtils
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customer")
class CustomerController(
    private val jwtUtils: JwtUtils,
    private val userService: UserService,
    private val readingService: ReadingService,
    private val voucherService: VoucherService
) {

    @GetMapping("/details")
    fun getCustomerDetail(
        @RequestHeader(value = "authorization", defaultValue = "") authToken: String
    ): ResponseEntity<Any> {
        try {
            jwtUtils.verify(authToken)
            val userInfo = jwtUtils.getUserInfo(authToken)

            val res = userService.loadUserByUsername(userInfo.customerId) ?: return ResponseEntity.ok()
                .body(Message("No record"))
            return ResponseEntity.ok().body(res.toCustomerDetails())
        } catch (e: Exception) {
            return ResponseEntity.badRequest().body(Message(e.toString()))
        }
    }

    @PostMapping("/submit-reading")
    fun submitReading(
        @RequestHeader(value = "authorization", defaultValue = "") authToken: String,
        @RequestBody newReading: NewReading
    ): ResponseEntity<Any> {
        try {
            jwtUtils.verify(authToken)
            val userInfo = jwtUtils.getUserInfo(authToken)

            val res = readingService.submitReading(userInfo.customerId, newReading)
            return ResponseEntity.ok().body(res)
        } catch (e: Exception) {
            return ResponseEntity.badRequest().body(Message(e.toString()))
        }
    }

    @GetMapping("/get-bill")
    fun getBill(
        @RequestHeader(value = "authorization", defaultValue = "") authToken: String,
    ): ResponseEntity<Any> {
        try {
            jwtUtils.verify(authToken)
            val userInfo = jwtUtils.getUserInfo(authToken)

            val bill = readingService.getRecentBill(userInfo.customerId)
            return if (bill == null) {
                ResponseEntity.status(404).body(Message("No pending bill."))
            } else {
                ResponseEntity.ok().body(bill)
            }
        } catch (e: Exception) {
            return ResponseEntity.badRequest().body(Message(e.toString()))
        }
    }

    @PostMapping("/pay-bill")
    fun payBill(
        @RequestHeader(value = "authorization", defaultValue = "") authToken: String,
    ): ResponseEntity<Any> {
        try {
            jwtUtils.verify(authToken)
            val userInfo = jwtUtils.getUserInfo(authToken)

            return readingService.payBill(userInfo.customerId)
        } catch (e: Exception) {
            return ResponseEntity.badRequest().body(Message(e.toString()))
        }
    }

    @GetMapping("/reading/{customerId}")
    fun getReading(
        @RequestHeader(value = "authorization", defaultValue = "") authToken: String,
        @PathVariable(name = "customerId") customerId: String
    ): ResponseEntity<Any> {
        try {
            jwtUtils.verify(authToken)

            val res = readingService.getReadingByCustomerId(customerId) ?: return ResponseEntity.ok()
                .body(Message("No record"))
            return ResponseEntity.ok().body(res)
        } catch (e: Exception) {
            return ResponseEntity.badRequest().body(Message(e.toString()))
        }
    }

    @PostMapping("/voucher-topup/{code}")
    fun creditTopup(
        @RequestHeader(value = "authorization", defaultValue = "") authToken: String,
        @PathVariable(name = "code") code: String
    ): ResponseEntity<Any> {
        return try {
            jwtUtils.verify(authToken)
            val userInfo = jwtUtils.getUserInfo(authToken)

            if (voucherService.isValidVoucher(code)) {
                voucherService.useVoucher(code)
                userService.addTopup(userInfo.customerId)
                ResponseEntity.ok().body(Message("Topup successful"))
            } else {
                ResponseEntity.badRequest().body(Message("Invalid Voucher Code"))
            }
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(Message(e.toString()))
        }
    }
}
