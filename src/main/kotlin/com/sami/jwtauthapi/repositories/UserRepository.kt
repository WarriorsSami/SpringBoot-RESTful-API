package com.sami.jwtauthapi.repositories

import com.sami.jwtauthapi.entities.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Int> {
    fun getUserByEmail(email: String): User?
    fun getUserById(id: Int): User?
}