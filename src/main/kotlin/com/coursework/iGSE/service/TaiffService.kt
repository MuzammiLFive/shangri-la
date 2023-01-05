package com.coursework.iGSE.service

import com.coursework.iGSE.repository.TaiffRepository
import org.springframework.stereotype.Service

@Service
class TaiffService(
    private val taiffRepository: TaiffRepository
) {

    fun updateTaiffRate(field: String, value: Float) {
        val data = taiffRepository.getAllByTaiffType(field)
        data.rate = value

        taiffRepository.save(data)
    }
}
