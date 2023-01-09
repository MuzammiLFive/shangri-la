package com.coursework.iGSE.repository

import com.coursework.iGSE.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<Customer, Long> {
    fun findByCustomerId(id: String): Customer?

    fun findAllByPropertyType(type: String): List<Customer?>

    @Query(
        "SELECT DISTINCT(customer_id) FROM customer WHERE property_type=?1 AND bedroom_num=?2",
        nativeQuery = true
    )
    fun findCustomerIdsByPropertyAnAndBedroomNum(type: String, bedCount: Int): List<String?>
}
