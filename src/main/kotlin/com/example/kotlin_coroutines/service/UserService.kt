package com.example.kotlin_coroutines.service

import com.example.kotlin_coroutines.model.User
import com.example.kotlin_coroutines.repository.UserRepository
import com.example.kotlin_coroutines.request.SearchRequest
import kotlinx.coroutines.flow.Flow
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class UserService(
    val userRepository: UserRepository
) {
    suspend fun saveUser(user: User): User?{
        return userRepository.saveUser(user)
    }

    suspend fun findById(request: SearchRequest): Flow<User> {
        val query = Query().addCriteria(Criteria().andOperator(createQuery(searchRequest = request)))
        return userRepository.find(query)
    }

    fun createQuery(searchRequest: SearchRequest): List<Criteria>{
        val criteria = mutableListOf<Criteria>()
        if (searchRequest.userId.isNotEmpty()){
            criteria.add(Criteria.where("userId").`is`(searchRequest.userId))
        }
        if(searchRequest.userName.isNotEmpty()){
            criteria.add(Criteria.where("login").`is`(searchRequest.userName))
        }
        return criteria
    }
}