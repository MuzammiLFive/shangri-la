package com.coursework.iGSE.service

import com.coursework.iGSE.entity.Reading
import com.coursework.iGSE.models.NewReading
import com.coursework.iGSE.models.Stats
import com.coursework.iGSE.models.toReading
import com.coursework.iGSE.repository.ReadingRepository
import org.springframework.stereotype.Service
import java.time.ZonedDateTime
import java.util.*

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

    fun submitReading(id: String, reading: NewReading) {
        if (reading.date > Date.from(ZonedDateTime.now().toInstant())) {
            throw Error("Invalid Date")
        }
        val pendingBill = readingRepository.getLastBillByCustomerId(id, "pending")
        if (pendingBill != null) {
            pendingBill.submissionDate = reading.date
            pendingBill.elecReadingDay = reading.electricityDay
            pendingBill.elecReadingNight = reading.electricityNight
            pendingBill.gasReading = reading.gas
            readingRepository.save(pendingBill)
        } else {
            readingRepository.save(reading.toReading(id))
        }
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
