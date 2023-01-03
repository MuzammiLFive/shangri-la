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

    fun save(user: Customer): Customer {
        return userRepository.save(user);
    }

//    fun loginUser(request: LoginRequest, user: CustomerEntity): LoginResponse {
//        val userPass = getSHA256(request.password);
//        if (userPass != user.passwordHash) {
//            throw UsernameNotFoundException("You have entered an invalid username or password")
//        } else {
//
//        }
//    }
}
