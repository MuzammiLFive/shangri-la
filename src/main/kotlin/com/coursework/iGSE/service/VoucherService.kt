package com.coursework.iGSE.service

import com.coursework.iGSE.repository.VoucherRepository
import org.springframework.stereotype.Service

@Service
class VoucherService(private val voucherRepository: VoucherRepository) {

    fun isValidVoucher(code: String): Boolean {
        return voucherRepository.findVoucherByEvcCode(code)?.used == 0
    }

    fun useVoucher(code: String) {
        val voucher = voucherRepository.findVoucherByEvcCode(code)?: throw NoSuchElementException()
        voucher.used = 1
        voucherRepository.save(voucher)
    }
}
