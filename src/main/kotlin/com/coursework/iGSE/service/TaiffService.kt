package com.coursework.iGSE.service

import com.coursework.iGSE.models.TaiffUpdate
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

    fun getTaiff(): TaiffUpdate {
        val elecDay = taiffRepository.getAllByTaiffType("electricity_day").rate
        val elecNight = taiffRepository.getAllByTaiffType("electricity_night").rate
        val gas = taiffRepository.getAllByTaiffType("gas").rate
        return TaiffUpdate(elecDay, elecNight, gas)
    }
}
