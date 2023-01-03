package com.coursework.iGSE.entity

import jakarta.persistence.*

enum class PropertyType {
    detached, `semi-detached`, terraced, flat, cottage, bungalow, mansion
}

enum class Role(val type: String) {
    admin("admin"),
    customer("customer")
}

@Entity
@Table(name = "customer")
class Customer(
    @Id
    @Column(name = "customer_id", unique = true)
    var customerId: String,

    @Column(name = "password_hash")
    var passwordHash: String,

    @Column(name = "address")
    var address: String,

    @Column(name = "property_type")
    var propertyType: String,

    @Column(name = "bedroom_num")
    var bedroomNum: Int,

    @Column(name = "balance")
    var balance: Double,

    @Column(name = "type")
    var type: String,
)
