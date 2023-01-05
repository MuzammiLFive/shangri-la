package com.coursework.iGSE.repository

import com.coursework.iGSE.entity.Reading
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ReadingRepository: JpaRepository<Reading, Long> {

    @Query("SELECT * FROM reading r WHERE r.customer_id=?1 ORDER BY r.submission_date DESC LIMIT 1", nativeQuery = true)
    fun getReadingByCustomerId(customerId: String): Reading?

    @Query("SELECT * FROM Reading", nativeQuery = true)
    fun getAllReadings(): List<Reading>

    @Query("SELECT DISTINCT(customer_id) from reading", nativeQuery = true)
    fun getDistinctCustomers(): List<String>
}
