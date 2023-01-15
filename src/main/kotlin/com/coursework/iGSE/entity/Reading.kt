package com.coursework.iGSE.entity

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "reading")
class Reading(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val reading_id: Long = -1L,

    @Column(name = "customer_id")
    val customerId: String,

    @Column(name = "submission_date")
    var submissionDate: Date,

    @Column(name = "elec_readings_day")
    var elecReadingDay: Float,

    @Column(name = "elet_reading_night")
    var elecReadingNight: Float,

    @Column(name = "gas_reading")
    var gasReading: Float,

    @Column(name = "status")
    var status: String
)
