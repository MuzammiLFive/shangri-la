package com.coursework.iGSE.repository

import com.coursework.iGSE.entity.Voucher
import org.springframework.data.jpa.repository.JpaRepository

interface VoucherRepository: JpaRepository<Voucher, Long> {
}
