package com.coursework.iGSE.service

import com.coursework.iGSE.entity.Customer
import com.coursework.iGSE.repository.UserRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    @Throws(UsernameNotFoundException::class)
    fun loadUserByUsername(username: String): Customer? {
        return userRepository.findByCustomerId(username)
    }

    fun addTopup(customerId: String) {
        val user = userRepository.findByCustomerId(customerId)!!
        user.balance += 200
        userRepository.save(user)
    }

    fun reduceBalance(customerId: String, amount: Float) {
        val user = userRepository.findByCustomerId(customerId)!!
        user.balance = user.balance - amount
        userRepository.save(user)
    }

    fun save(user: Customer): Customer {
        return userRepository.save(user);
    }
}
