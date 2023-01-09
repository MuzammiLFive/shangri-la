package com.coursework.iGSE.service

import com.coursework.iGSE.entity.PropertyType
import com.coursework.iGSE.entity.Reading
import com.coursework.iGSE.repository.ReadingRepository
import com.coursework.iGSE.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class PropertyService(
    private val userRepository: UserRepository,
    private val readingRepository: ReadingRepository
) {

    fun getPropertyCount(): HashMap<String, Int> {
        val theMap = HashMap<String, Int>()
        PropertyType.values().forEach { type ->
            run {
                val values = userRepository.findAllByPropertyType(type.name)
                val count = values.size
                theMap[type.name] = count
            }
        }
        return theMap
    }

    fun getStatisticsTypeBedCount(type: String, bedCount: Int): HashMap<String, Any> {
        val theMap = HashMap<String, Any>()
        val customerIds = userRepository.findCustomerIdsByPropertyAnAndBedroomNum(type, bedCount)
        var readingsCongregated: List<Reading> = mutableListOf()

        customerIds.forEach { id ->
            run {
                val readings = readingRepository.getAllByCustomerId(id!!)
                if (readings.isNotEmpty())
                    readingsCongregated += readings
            }
        }
        var totalSum = 0.0
        readingsCongregated.map { reading ->
            totalSum += reading.gasReading + reading.elecReadingDay + reading.elecReadingNight
        }

        theMap["type"] = type
        theMap["bedroom"] = bedCount
        theMap["average_electricity_gas_cost_per_day"] = totalSum / readingsCongregated.size
        theMap["unit"] = "pound"
        return theMap
    }
}
