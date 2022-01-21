package com.example.kotlin_coroutines.service

import com.example.kotlin_coroutines.model.User
import com.example.kotlin_coroutines.repository.UserRepository
import com.example.kotlin_coroutines.request.SearchRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class UserService(
    val userRepository: UserRepository
) {
    suspend fun saveUser(user: User): User?{
        return userRepository.save(user).awaitSingleOrNull()
    }

    suspend fun findById(userId:String):User?{
        return userRepository.findById(userId).awaitSingleOrNull()
    }

    suspend fun getAllUsers():Flow<User>{
        return userRepository.findAll().asFlow()
    }

    suspend fun saveAll(userList:List<User>):Flow<User> {
        return userRepository.saveAll(userList).asFlow()
    }

    suspend fun updateUser(){}
}