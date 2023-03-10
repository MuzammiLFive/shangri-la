package com.coursework.iGSE.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "voucher")
class Voucher(
    @Id
    @Column(name = "EVC_code")
    var evcCode: String,

    @Column(name = "used")
    var used: Int
)
