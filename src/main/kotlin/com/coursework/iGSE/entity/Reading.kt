package com.coursework.iGSE.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.Date

@Entity
@Table(name = "reading")
class Reading(
    @Id
    @Column(name = "reading_id")
    @GeneratedValue
    val readingId: Int,

    @Column(name = "customer_id")
    val customerId: String,

    @Column(name = "submission_date")
    val submissionDate: Date,

    @Column(name = "elec_readings_day")
    val elecReadingDay: Float,

    @Column(name = "elet_reading_night")
    val elecReadingNight: Float,

    @Column(name = "gas_reading")
    val gasReading: Float,

    @Column(name = "status")
    val status: String
)
