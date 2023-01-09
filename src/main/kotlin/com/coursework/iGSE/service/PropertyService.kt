package com.coursework.iGSE.service

import com.coursework.iGSE.entity.PropertyType
import com.coursework.iGSE.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class PropertyService(
    private val userRepository: UserRepository
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
}
