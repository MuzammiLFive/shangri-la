package com.coursework.iGSE.repository

import com.coursework.iGSE.entity.Reading
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ReadingRepository: JpaRepository<Reading, Long> {
    fun getReadingByCustomerId(customerId: String): Reading?

    @Query("SELECT * FROM Reading", nativeQuery = true)
    fun getAllReadings(): List<Reading>
}
