package com.coursework.iGSE.repository

import com.coursework.iGSE.entity.Reading
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ReadingRepository : JpaRepository<Reading, Long> {

    @Query("SELECT * FROM reading r WHERE r.customer_id=?1 ORDER BY r.submission_date DESC LIMIT 1", nativeQuery = true)
    fun getReadingByCustomerId(customerId: String): Reading?

    @Query("SELECT * FROM Reading", nativeQuery = true)
    fun getAllReadings(): List<Reading>

    fun getAllByCustomerId(id: String): List<Reading>

    @Query("SELECT DISTINCT(customer_id) from reading", nativeQuery = true)
    fun getDistinctCustomers(): List<String>

    @Query(
        "SELECT * FROM reading WHERE customer_id= ?1 AND status=?2 ORDER BY submission_date DESC LIMIT 1",
        nativeQuery = true
    )
    fun getLastBillByCustomerId(id: String, status: String): Reading?
}
