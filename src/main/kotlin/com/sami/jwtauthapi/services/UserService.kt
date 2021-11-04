package com.sami.jwtauthapi.services

import com.sami.jwtauthapi.entities.User
import com.sami.jwtauthapi.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun saveUser(user: User): User {
        return userRepository.save(user)
    }

    fun getUserByEmail(email: String): User? {
        return userRepository.getUserByEmail(email)
    }

    fun getUserById(id: Int): User? {
        return userRepository.getUserById(id)
    }

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }
}