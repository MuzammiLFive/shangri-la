package com.coursework.iGSE.service

import com.coursework.iGSE.entity.Reading
import com.coursework.iGSE.repository.ReadingRepository
import org.springframework.stereotype.Service

@Service
class ReadingService(
    private val readingRepository: ReadingRepository
) {
    fun getReadingByCustomerId(customerId: String): Reading? {
        return readingRepository.getReadingByCustomerId(customerId)
    }

    fun getAllReadings(): List<Reading> {
        return readingRepository.getAllReadings()
    }
}
