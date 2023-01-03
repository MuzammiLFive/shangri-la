package com.coursework.iGSE.repository

import com.coursework.iGSE.entity.Reading
import org.springframework.data.jpa.repository.JpaRepository

interface ReadingRepository: JpaRepository<Reading, Long> {
}
