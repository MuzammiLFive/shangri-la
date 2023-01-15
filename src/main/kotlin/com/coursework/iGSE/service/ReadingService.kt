package com.coursework.iGSE.service

import com.coursework.iGSE.entity.Reading
import com.coursework.iGSE.models.*
import com.coursework.iGSE.repository.ReadingRepository
import org.apache.coyote.Response
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.ZonedDateTime
import java.util.*

@Service
class ReadingService(
    private val readingRepository: ReadingRepository,
    private val taiffService: TaiffService,
    private val userService: UserService
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

    fun getRecentBill(id: String): Bill? {
        val lastPaid = readingRepository.getLastBillByCustomerId(id, "paid")
        val lastPending = readingRepository.getLastBillByCustomerId(id, "pending")
        return if (lastPending == null) {
            null
        } else {
            val taiff = taiffService.getTaiff()
            val elecDayUse = lastPending.elecReadingDay - (lastPaid?.elecReadingDay ?: 0F)
            val elecNightUse = lastPending.elecReadingNight - (lastPaid?.elecReadingNight ?: 0F)
            val gasUse = lastPending.gasReading - (lastPaid?.gasReading ?: 0F)
            Bill(
                elecDayUse,
                elecNightUse,
                gasUse,
                (elecDayUse * taiff.electricityDay!! + elecNightUse * taiff.electricityNight!! + gasUse * taiff.gas!!)
            )
        }
    }

    fun payBill(id: String): ResponseEntity<Any> {
        val record = readingRepository.getLastBillByCustomerId(id, "pending")
        val bill = this.getRecentBill(id)
        if (record == null || bill == null) {
            return ResponseEntity.badRequest().body(Message("No pending bill."))
        }
        record.status = "paid"
        readingRepository.save(record)
        userService.reduceBalance(id, bill.total)

        return ResponseEntity.ok().body(Message("Payment Complete"))
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
