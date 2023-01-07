package com.coursework.iGSE.models

import com.coursework.iGSE.entity.PropertyType

data class RegisterRequest(
    val email: String,
    val password1: String,
    val password2: String,
    val address: String,
    val propertyType: PropertyType,
    val bedroomNum: Int,
    val voucher: String
)

data class LoginRequest(
    val email: String,
    val password: String,
)

data class LoginResponse(
    val customerId: String,
    val role: String,
    val token: String
)

data class UserInfo(
    val customerId: String,
    val role: String
)

data class TaiffUpdate(
    val electricityDay: Float?,
    val electricityNight: Float?,
    val gas: Float?
)

data class Stats(
    val electricityAvg: Double,
    val gasAvg: Double
)
