package com.coursework.iGSE.service

import com.coursework.iGSE.entity.Reading
import com.coursework.iGSE.models.Stats
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

    fun statistics(): Stats {
        val users = readingRepository.getDistinctCustomers()
        val electricityReading = mutableListOf<Float>()
        val gasReading = mutableListOf<Float>()
        users.map { user ->
            val record = readingRepository.getReadingByCustomerId(user)
            electricityReading.add(record!!.elecReadingDay)
            gasReading.add(record.gasReading)
        }
        return Stats(electricityReading.average(), gasReading.average())
    }
}
