package com.coursework.iGSE.repository

import com.coursework.iGSE.entity.Taiff
import org.springframework.data.jpa.repository.JpaRepository

interface TaiffRepository: JpaRepository<Taiff, Long> {
    fun getAllByTaiffType(id: String): Taiff
}
