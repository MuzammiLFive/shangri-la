package com.coursework.iGSE.repository

import com.coursework.iGSE.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<Customer, Long> {
    fun findByCustomerId(id: String): Customer?

    fun findAllByPropertyType(type: String): List<Customer?>
}
