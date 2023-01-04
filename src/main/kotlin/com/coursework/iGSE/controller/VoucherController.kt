package com.coursework.iGSE.controller

import com.coursework.iGSE.models.Message
import com.coursework.iGSE.service.VoucherService
import com.coursework.iGSE.utils.JwtUtils
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.Exception

@RestController
@RequestMapping("/api")
class VoucherController(
    private val jwtUtils: JwtUtils,
    private val voucherService: VoucherService
) {

    @GetMapping("/check-voucher/{code}")
    fun checkVoucher(
        @RequestHeader(value = "authorization", defaultValue = "") authToken: String,
        @PathVariable("code") code: String
    ): ResponseEntity<Any> {
        try {
            jwtUtils.verify(authToken)

            val res = voucherService.isValidVoucher(code)
            if (!res) {
                return ResponseEntity.badRequest().body(Message("Invalid Voucher"))
            }
            return ResponseEntity.ok().body(Message("Valid Voucher"))
        } catch (e: Exception) {
            return ResponseEntity.badRequest().body(Message(e.toString()))
        }
    }

    @PostMapping("/use-voucher/{code}")
    fun useVoucher(
        @RequestHeader(value = "authorization", defaultValue = "") authToken: String,
        @PathVariable("code") code: String
    ): ResponseEntity<Any> {
        try {
            jwtUtils.verify(authToken)

            val res = voucherService.isValidVoucher(code)
            if (!res) {
                return ResponseEntity.badRequest().body(Message("Invalid Voucher"))
            }
            voucherService.useVoucher(code);
            return ResponseEntity.ok().body(Message("Valid Voucher"))
        } catch (e: Exception) {
            return ResponseEntity.badRequest().body(Message(e.toString()))
        }
    }
}
