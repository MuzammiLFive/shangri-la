package com.coursework.iGSE.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "taiff")
class Taiff(
    @Id
    @Column(name = "taiff_type")
    val taiffType: String,

    @Column(name = "rate")
    var rate: Float
)
